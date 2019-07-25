package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    List<Setmeal> findPage(@Param("queryString") String queryString);

    void add(Setmeal setmeal);

    void setSetmealAndCheckgroup(@Param("batchParam") ArrayList<Map> batchParam);

    List<Setmeal> findAllsetmeal();

    Setmeal findsetmealDetailsById(@Param("id") Integer id);

    List<CheckGroup> findCheckGroupById(@Param("id") Integer id);

    List<CheckItem> findAllCheckItem(@Param("checkGroupId") Integer checkGroupId);

    List<CheckItem> findCheckItemsByBeachId(@Param("checkGroupIds") List<Integer> checkGroupIds);

    Setmeal findDetailById(@Param("id") Integer id);
}
