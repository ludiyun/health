package com.itheima.Controller;

import Utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.*;
import com.itheima.pojo.Setmeal;
import com.itheima.service.Interface.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;


@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    String QINIUURL = "http://putcv7ymz.bkt.clouddn.com";

    @Reference
    private SetmealService setmealService;
    @Autowired
    JedisPool jedisPool;


    /*
     * 文件上传
     * 参数是基本类型,数组,MultipartFile是不需要加@RequestParam的
     *
     * */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
//动态文件名称
            //获取原始文件名称
            String originalFilename = imgFile.getOriginalFilename();
            //获取文件类型
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            //利用UUID动态获取文件,拼接
            String fileName = UUID.randomUUID().toString() + substring;


            //调用七牛上传文件
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
/*将图片存入Redis中*/
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);


            //拼接返回的url
            String fullImgUrl = QINIUURL + "/" + fileName;

            return Result.success(MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (Exception e) {
            return Result.error(MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean);

    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
            /*将文件存入Redis中*/
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());


            return Result.success(MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return Result.error(MessageConstant.ADD_SETMEAL_FAIL);

        }


    }
}
