package com.itheima.service.Interface;

import Vo.CheckGroupVo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckgroupService {
    void add(List<Integer> checkitemIds,CheckGroup  checkgroup);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroupVo findForUpdate(Integer id);


    void edit(CheckGroup checkGroup);

    List<CheckGroup> fingAll();
}
