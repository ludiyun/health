package com.itheima.Impl;

import Vo.CheckGroupVo;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckgroupMapper;
import com.itheima.dao.CheckitemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.Interface.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = CheckgroupService.class )
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {
   @Autowired
    private CheckgroupMapper checkgroupMapper;
 @Autowired
    private CheckitemMapper checkitemMapper;

    @Override
    public void add(List<Integer> checkitemIds,CheckGroup checkgroup) {
        //新增检查组
        checkgroupMapper.add(checkgroup);
        Integer checkgroupId = checkgroup.getId();

        /*设置检查组和检查项的关系*/
        ArrayList<Map> batchParam = new ArrayList<>();
        if(checkitemIds!=null && checkitemIds.size()>0){
                for (Integer checkitemId : checkitemIds) {
                    Map map = new HashMap();
                    map.put("checkgroupId",checkgroupId);
                    map.put("checkitemId",checkitemId);
                    batchParam.add(map);
                }
            }
        //保存检查组和检查项关系
        checkgroupMapper.setcheckgroupAndcheckitem(batchParam);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        /*使用分页插件*/
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<CheckGroup> checkgroups= checkgroupMapper.findPage(queryPageBean.getQueryString());

//返回分页结果,根据PageResult的构造函数确定结果对象的参数
        return new PageResult(page.getTotal(),checkgroups);
    }

    @Override
    public CheckGroupVo findForUpdate(Integer id){
        //        1:根据检查组id查询检查组信息,
        CheckGroupVo checkGroupVo = new CheckGroupVo();
        CheckGroup checkGroup=  checkgroupMapper.findById(id);
      checkGroupVo.setCheckGroup(checkGroup);


//                2:根据检查组id查询检查项信息
        List<CheckItem> checkItems = checkitemMapper.findAll();
        checkGroupVo.setCheckItems(checkItems);


//        3:根据检查组id查询被勾选的检查项信息
        List<Integer> list= checkgroupMapper.findcheckgroupAndcheckitem(id);
        checkGroupVo.setCheckitemIds(list);


        return checkGroupVo;
    }


    /*编辑插入数据*/
    @Override
    public void edit(CheckGroup checkGroup) {

        System.out.println(checkGroup);
        /*1:插入检查组数据到检查组表中*/
        checkgroupMapper.edit(checkGroup);
        Integer checkGroupId = checkGroup.getId();

        /*2:删除原有的两个表的关系*/
        checkgroupMapper.deleteAssociation(checkGroupId);


        /*3;保存检查组和检查项的关系到检查组和检查项的中间表中*/
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();
        ArrayList<Map> list = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(checkitemIds)){
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                list.add(map);
            }
        }
        checkgroupMapper.setcheckgroupAndcheckitemEdit(list);
    }

    @Override
    public List<CheckGroup> fingAll() {
        List<CheckGroup> checkGroups = checkgroupMapper.fingAll();
        return checkGroups;
    }

}
