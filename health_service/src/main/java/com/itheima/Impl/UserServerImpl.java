package com.itheima.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = UserService.class)
public class UserServerImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public String selectUserByid(Long id) {

        User user = userMapper.queryUserName(id);
        return "jack"+ user;
    }
}
