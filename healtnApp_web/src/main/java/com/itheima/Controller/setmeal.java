package com.itheima.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.Interface.SetmealService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class setmeal {
    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result findAllsetmeal() {
        try {
            List<Setmeal> setmeals = setmealService.findAllsetmeal();
            return Result.success(MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeals);
        } catch (Exception e) {
            return Result.error(MessageConstant.QUERY_SETMEALLIST_FAIL);

        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
//        Setmeal setmeal =setmealService.findById(id);
            Setmeal setmeal = setmealService.findBeachById(id);

            return Result.success("", setmeal);
        } catch (Exception e) {
            return Result.error("网络异常");
        }


    }

    public static void main(String[] args) {
        String url = "setmeal_detail.html?id=12&name=张三&age=18";
        System.out.println(getSetmealId(url, "id"));

    }

    public static String getSetmealId(String url, String key) {

        String str = url.split("\\?")[1];
        String[] split = str.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            if (split1[0].equals(key)) {

                return split1[1];
            }
        }
        return null;
    }

    @RequestMapping("/findDetailById")
    public Result findDetailById(Integer id) {

        try {
            Setmeal setmeal =setmealService.findDetailById(id);
            return Result.success("",setmeal);
        } catch (Exception e) {
            return Result.error("网络加载错误");
        }
    }
}
