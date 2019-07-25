package com.itheima.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckitemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.Interface.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceImpl implements CheckitemService {
    /*引入Mapper层*/
    @Autowired
    public CheckitemMapper checkitemMapper;

    /*如果是pojo,map不需要加@param
    * ,其他的最好都加上*/
    @Override
    public void add(CheckItem checkItem) {
checkitemMapper.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        /*使用分页插件*/
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<CheckItem> checkItemList= checkitemMapper.findPage(queryPageBean.getQueryString());

//返回分页结果,根据PageResult的构造函数确定结果对象的参数
        return new PageResult(page.getTotal(),checkItemList);
    }

    @Override
    public void delete(Integer id) throws RuntimeException{
        //查询检查项是否被引用
               Integer count= checkitemMapper.findCountByCheckitemId(id);
        //如果被引用,提示不能删除
        if(null!=count && count>0){

            throw new RuntimeException("数据被引用,不能删除");
        }
        else{
            //否则删除
            checkitemMapper.delete(id);
        }

    }

    @Override
    public CheckItem findById(Integer id) {
        return checkitemMapper.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
         checkitemMapper.edit(checkItem);
    }


    @Override
    public List<CheckItem> findAll() {
        return checkitemMapper.findAll();
    }
}
