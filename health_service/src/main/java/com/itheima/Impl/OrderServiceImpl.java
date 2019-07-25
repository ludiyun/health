package com.itheima.Impl;

import Vo.OrderInfoVo;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrdersettingMapper;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result submit(OrderInfoVo orderInfoVo) {
        String telephone = orderInfoVo.getTelephone();
        String orderDate1 = orderInfoVo.getOrderDate();
        DateTime orderDate = DateUtil.parse(orderDate1, "yyyy-MM-dd");
        String name = orderInfoVo.getName();
        String orderType = orderInfoVo.getOrderType();
        String sex = orderInfoVo.getSex();
        String idCard = orderInfoVo.getIdCard();
        String setmealstr= orderInfoVo.getSetmealId();
        Integer setmealId = Integer.valueOf(setmealstr);
//检查用户的预约日期是否存在预约设置



            OrderSetting orderSetting = ordersettingMapper.findByDate(orderDate);
            if (null == orderSetting) {
                return Result.error("没有档期");

            }
//否则获取已经预约数量与可预约数量进行比较
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();

            //查询可预约人数是否充足
            if (reservations >= number) {
                return Result.error("预约人数已满,请更换医生或更换日期");
            }


        //查询当前客户是否是我们的会员
        Member member = memberDao.findByTelephone(telephone);


        if (null == member) {
            //如果当前用户不是会员,就要收集用户信息
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setIdCard(idCard);
            member.setName(name);
            member.setRegTime(new Date());
            member.setSex(sex);
            memberDao.add(member);

        }

        /*获取用户id,查询订单信息*/
        Integer memberId = member.getId();


        //检查用户是否重复
        Integer memberCount = orderDao.findMemberCountById(memberId);
        if (null != memberCount && memberCount > 0) {
            return Result.error("不可重复预约");
        }
        //直接下单

        Order order = new Order(memberId, orderDate, Order.ORDERTYPE_WEIXIN,Order.ORDERSTATUS_NO, setmealId);

        orderDao.add(order);
        //已经预约人数+1
      int count=ordersettingMapper.updateReservationsByorderDate(orderDate);
      /*跟新一条数据,会返回一个数值*/
      if(count!=1){
          throw new RuntimeException("下单失败");
      }
        return Result.success("预约成功",order);
    }
}

