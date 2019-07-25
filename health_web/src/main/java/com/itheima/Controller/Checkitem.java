package com.itheima.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.Interface.CheckitemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class Checkitem {

    /*注入service层对象*/
    @Reference
    CheckitemService checkitemService;


    /*    参数类型是Map,pojo需要加RequestBody
    参数类型是常量，不需要加,前提是页面参数名称要与Controller形参一样
    参数类型是LIstanbul，要加RequestParam("参数名")
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {//返回checkItem 对象
        /*返回常量,Result类中封装了返回结果*/
        /*返回成功信息*/
        try {
            checkitemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }
        /*失败返回信息*/ catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkitemService.findPage(queryPageBean);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        /*根据返回的结果返回不同的提示信息到前端*/
        try {
            checkitemService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
            /*抓取service层抛出的错误*/
        }
        catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        }
        catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }

    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem=checkitemService.findById(id);
            return Result.success("查询检查详情成功",checkItem);
        } catch (Exception e) {
           return Result.error("查询检查详情失败");
        }

    }


    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        checkitemService.edit(checkItem);
        try {
            return Result.success(MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            return Result.error(MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }


    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItems =checkitemService.findAll();
        if(null!=checkItems && checkItems.size()>0){
            return Result.success(MessageConstant.ADD_CHECKGROUP_SUCCESS,checkItems);
        }
        else{
            return Result.error("网络错误,请联系管理员");
        }
    }
}