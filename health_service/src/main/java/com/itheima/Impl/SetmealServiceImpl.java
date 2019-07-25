package com.itheima.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.Interface.SetmealService;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //分页插件
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //查询数据
        List<Setmeal> setmeals = setmealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), setmeals);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加数据到检查组中
        setmealMapper.add(setmeal);


        //保存检查组和套餐的关系
        setSetmealAndCheckgroup(setmeal, checkgroupIds);

    }


    public void setSetmealAndCheckgroup(Setmeal setmeal, Integer[] checkgroupIds) {
        Integer setmealId = setmeal.getId();
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            ArrayList<Map> batchParam = new ArrayList<>();
            for (Integer checkgroupId : checkgroupIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkgroupId);
                map.put("setmealId", setmealId);
                batchParam.add(map);
            }
            setmealMapper.setSetmealAndCheckgroup(batchParam);
        }

    }

    /*app端查询套餐列表数据*/
    @Override
    public List<Setmeal> findAllsetmeal() {
        return setmealMapper.findAllsetmeal();
    }

    //    app查询套餐详情,检查组,检查项
/*    @Override
    public Setmeal findById(Integer id) {
        //查询套餐详情
        Setmeal setmeal = setmealMapper.findsetmealDetailsById(id);
        //查询检查组
        List<CheckGroup> checkGroups = setmealMapper.findCheckGroupById(id);
        if (CollectionUtil.isNotEmpty(checkGroups)) {
            //循环遍历查询检查组下面的检查项
            for (CheckGroup checkGroup : checkGroups) {
                List<CheckItem> checkItems = setmealMapper.findAllCheckItem(checkGroup.getId());
//                将检查项放到检查组里
                checkGroup.setCheckItems(checkItems);
            }
//            将检查组F放到套餐里
            setmeal.setCheckGroups(checkGroups);
        }

        return setmeal;
    }*/

    /*APP端批量查询*/
    @Override
    public Setmeal findBeachById(Integer id) {


        //查询套餐详情
        Setmeal setmeal = setmealMapper.findsetmealDetailsById(id);
        //查询套餐下的所有检查组
        List<CheckGroup> checkGroups = setmealMapper.findCheckGroupById(id);
        if (CollectionUtil.isNotEmpty(checkGroups)) {
            List<Integer> checkGroupIds = getCheckGroupIds(checkGroups);
            /*通过所有检查组id进行批量查询检查项*/
            List<CheckItem> checkItems = setmealMapper.findCheckItemsByBeachId(checkGroupIds);

            /*将检查组下面的检查项按照checkGroupId进行分组*/
            Map<Integer, List<CheckItem>> IntegetListMap = checkItems.stream().
                    collect(Collectors.groupingBy(CheckItem::getCheckGroupId));


            for (CheckGroup checkGroup : checkGroups) {
                /*将对应的检查项集合方法对应的检查组中*/
                checkGroup.setCheckItems(IntegetListMap.get(checkGroup.getId()));
            }
            setmeal.setCheckGroups(checkGroups);

        }
        return setmeal;
    }

    @Override
    public Setmeal findDetailById(Integer id) {
        return setmealMapper.findDetailById(id);
    }

    /*获取所有的检查组id,*/
    public List<Integer> getCheckGroupIds(List<CheckGroup> checkGroups) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CheckGroup checkGroup : checkGroups) {
            ids.add(checkGroup.getId());
        }

        return ids;
    }
}




