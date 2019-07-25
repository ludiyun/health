package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/*如果是pojo,map不需要加上@Param注解,其他的需要加上*/
public interface CheckitemMapper {
    void add(CheckItem checkItem);

    List<CheckItem> findPage(@Param("queryString") String queryString);

    Integer findCountByCheckitemId(@Param("id")Integer id);

    void delete(@Param("id")Integer id);

    CheckItem findById(@Param("id")Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

}
