package com.itheima.Controller;

import Vo.CheckGroupVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.Interface.CheckgroupService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class Checkgroup{

    @Reference
    private CheckgroupService checkgroupService;


    @RequestMapping("/add")
public Result add(@RequestParam("checkitemIds")List<Integer> checkitemIds, @RequestBody CheckGroup  checkgroup){
        try {
            checkgroupService.add(checkitemIds,checkgroup);
        } catch (Exception e) {
//            新增失敗
            return Result.error(MessageConstant.ADD_CHECKGROUP_FAIL);
        }
//新增成功
return Result.success(MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkgroupService.findPage(queryPageBean);
    }

    /*编辑回显*/
    @RequestMapping("findForUpdate")
    public Result findForUpdate(Integer id){
        try {
            CheckGroupVo CheckGroupVo = checkgroupService.findForUpdate(id);
            return Result.success("",CheckGroupVo);
        } catch (Exception e) {
          return Result.error("网络异常");
        }
    }

    /*编辑插入*/
    @RequestMapping("/edit")
    Result edit(@RequestBody CheckGroup checkGroup){
        try {
            System.out.println("checkGroup = " + checkGroup);
            checkgroupService.edit(checkGroup);
            return Result.success(MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
          return Result.error(MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

@RequestMapping("/fingAll")
public Result fingAll(){
    try {
        List<CheckGroup> checkGroups=checkgroupService.fingAll();
        return Result.success("",checkGroups);
    } catch (Exception e) {
       return Result.error("获取检查组失败");
    }


}

}
