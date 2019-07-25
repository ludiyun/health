package com.itheima.dao;

import org.apache.ibatis.annotations.Param;
import com.itheima.pojo.User;

public interface UserMapper {
    public User queryUserName(@Param("id") long id);
}
