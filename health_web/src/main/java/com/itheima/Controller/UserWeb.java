package com.itheima.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.Interface.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

@Controller
@RequestMapping("hello")
public class UserWeb implements Serializable {
    @Reference
    private UserService userService;

    @RequestMapping("hello")
    public String selectUserController(Long id,Model model
    ){
        String s = userService.selectUserByid(id);
        model.addAttribute("users",s);
        System.out.println("s = " + s);
        return "/WEB-INF/views/helloUser.jsp";
    }

}
