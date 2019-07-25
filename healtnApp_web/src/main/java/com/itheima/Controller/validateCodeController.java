package com.itheima.Controller;

import Utils.ValidateCodeUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.Interface.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class validateCodeController {
    @Reference
    private ValidateCodeService validateCodeService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            /*获取验证码的长度*/
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            System.out.println("code = " + code);
            /*将验证码放到redis缓存中,等到提交的时候比较,与保存图片一样*/
            /*setex():将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。将验证码关联到手机号,并设置时间 */
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER + telephone, 30 * 60, String.valueOf(code));

            return Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (Exception e) {
            return Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }



}
