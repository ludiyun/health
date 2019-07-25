package com.itheima.service.Interface;

import com.itheima.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;

public interface OrdersettingService {
    void editNumberByDate(OrderSetting orderSetting);

    void add(ArrayList<OrderSetting> ordersettings);

    List<OrderSetting> getOrderSettingByMonth(String month);
}
