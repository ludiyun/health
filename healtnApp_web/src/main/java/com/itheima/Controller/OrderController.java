package com.itheima.Controller;


import Vo.OrderInfoVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.Interface.OrderService;
import exception.OrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody OrderInfoVo orderInfoVo) {
        String telephone = orderInfoVo.getTelephone();//获取手机号
        String validateCode = orderInfoVo.getValidateCode();//获取验证码

//拿取token
        String token = orderInfoVo.getToken();
        if (token == null) {
            return Result.error("非法请求");

        }
        String redisTooken = jedisPool.getResource().get(token);
        /*只要进来了就表示是正确的token,不用两者进行比较了,删除内存中原有的token*/
        if (null != redisTooken) {

            jedisPool.getResource().del(redisTooken);
        }else {
            return Result.error("非法请求");

        }
        String redisCode = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + telephone);
        //验证码正确但是如果过时
        if (null == redisCode) {
            return Result.error("验证码错误,请重新输入");
        }
        //验证码不正确
        if (!redisCode.equals(validateCode)) {
            return Result.error("验证码错误,请重新输入");
        }

        Result result = null;
        /*由于加了乐观锁,如果商品不止一件,可以用户提交失败,可以帮助用户在提交两次*/
        try {
            result = orderService.submit(orderInfoVo);
        } catch (OrderException e) {
            for (int i = 0; i < 2; i++) {
                result = orderService.submit(orderInfoVo);

            }
        }

//如果登录成功,发送短信
        if (result.isFlag()) {
//            JuheSmsUtils.sengYZMSms(telephone);
        }
        return result;
    }


    /*创建token,将token存放到redis中,并且将token返回给客户端,
    下次用户再来的时候带来*/
    @RequestMapping("/getToken")
    public Result getToken() {
        //获取token
        String token = UUID.randomUUID().toString();
//放入到redis缓存中
        jedisPool.getResource().setex(token, 30 * 60, token);
        return Result.success("", token);

    }

    public static void main(String[] args) {
        String token = UUID.randomUUID().toString();
        System.out.println("token = " + token);
    }
}
