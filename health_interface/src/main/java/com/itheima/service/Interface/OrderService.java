package com.itheima.service.Interface;

import Vo.OrderInfoVo;
import com.itheima.entity.Result;

public interface OrderService {
    Result submit(OrderInfoVo orderInfoVo);
}
