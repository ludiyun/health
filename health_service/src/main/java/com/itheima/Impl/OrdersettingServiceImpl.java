package com.itheima.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrdersettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.Interface.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {
    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //查询数据库是否已经存在
        Integer count = ordersettingMapper.findNumberByDate(orderSetting.getOrderDate());

        //存在,更新预约设置
        if (count > 0) {
            ordersettingMapper.updateNumberByDate(orderSetting);
        } else {
            //不存在,添加预约设置
            ordersettingMapper.add(orderSetting);

        }

    }

    @Override
    public void add(ArrayList<OrderSetting> ordersettings) {

        if (CollectionUtil.isNotEmpty(ordersettings)) {
            for (OrderSetting orderSetting : ordersettings) {
                editNumberByDate(orderSetting);
            }
        }

    }

    @Override
    public List<OrderSetting> getOrderSettingByMonth(String month) {
        return ordersettingMapper.getOrderSettingByMonth(month);

    }
}
