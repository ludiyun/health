package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CheckgroupMapper {

    void add(CheckGroup checkgroup);

        List<CheckGroup> findPage(@Param("queryString") String queryString);

    CheckGroup findById(@Param("id") Integer id);

//    List<CheckItem> findAll(Integer id);

    List<Integer> findcheckgroupAndcheckitem(@Param("checkGroupId")Integer id);

    void setcheckgroupAndcheckitem(@Param("batchParam") ArrayList<Map> batchParam);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(@Param("id") Integer id);


    /*编辑2保存关系*/
    void setcheckgroupAndcheckitemEdit(@Param("list") ArrayList<Map> list);

    List<CheckGroup> fingAll();

}
