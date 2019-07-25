package com.itheima.Controller;

import Utils.POIUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.Interface.OrdersettingService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class Ordersetting {

    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            ordersettingService.editNumberByDate(orderSetting);
            return Result.success(MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            return Result.error(MessageConstant.ORDERSETTING_FAIL);
        }
    }

    /*批量导入表格数据*/
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            ArrayList<OrderSetting> ordersettings = new ArrayList<>();
            List<String[]> orderArray = POIUtils.readExcel(excelFile);
            if (CollectionUtil.isNotEmpty(orderArray)) {
                for (String[] strings : orderArray) {
                    if (strings.length == 2) {
                        String datastr = strings[0];
                        String numberstr = strings[1];
                        /*将读取个表格里的字段解析格式封装到orderSetting对象中*/
                        OrderSetting orderSetting = new OrderSetting(DateUtil.parse(datastr, "yy/MM/dd").toJdkDate(), Integer.valueOf(numberstr));
                        ordersettings.add(orderSetting);
                    }
                }
            }
            ordersettingService.add(ordersettings);
            return Result.success(MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {

            return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        try {
            List<OrderSetting> orderSettings = ordersettingService.getOrderSettingByMonth(month);
            return Result.success(MessageConstant.IMPORT_ORDERSETTING_SUCCESS, orderSettings);
        } catch (Exception e) {
            return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);

        }

    }
}
