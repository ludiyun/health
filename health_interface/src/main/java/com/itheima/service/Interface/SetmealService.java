package com.itheima.service.Interface;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Setmeal setmeal,Integer[] checkgroupIds);

    List<Setmeal> findAllsetmeal();

//    Setmeal findById(Integer id);

    Setmeal findBeachById(Integer id);


    Setmeal findDetailById(Integer id);
}
