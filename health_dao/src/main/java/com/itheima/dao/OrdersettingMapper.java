package com.itheima.dao;

import cn.hutool.core.date.DateUtil;
import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrdersettingMapper {
    Integer findNumberByDate(@Param("orderDate") Date orderDate);

    void updateNumberByDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(@Param("month") String month);

    /*查询用户的预约日期是否存在预约设置,需要得到已预约人数和可预约人数,所以用OrderSetting来接收*/
    OrderSetting findByDate(@Param("orderDate")Date orderDate );


    Integer updateReservationsByorderDate(@Param("orderDate") Date orderDate);
}

