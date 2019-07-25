package com.itheima.service.Interface;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckitemService {
    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    public List<CheckItem> findAll();

}
