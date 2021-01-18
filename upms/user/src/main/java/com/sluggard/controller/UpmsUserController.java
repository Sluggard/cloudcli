package com.sluggard.controller;


import com.sluggard.common.vo.ResponseResult;
import com.sluggard.entity.UpmsUser;
import com.sluggard.feign.vo.Customer;
import com.sluggard.mybatis.vo.PageQuery;
import com.sluggard.service.UpmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


/**
 * <p>
    * 系统后台用户信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsUserController",tags={"系统后台用户信息操作接口"})
@RequestMapping("/v1/upmsUser")
public class UpmsUserController {

    @Autowired
    private UpmsUserService upmsUserService;

    @ApiOperation(value = "查询系统后台用户信息", notes = "查询系统后台用户信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsUserService.getById(id));
    }

    @ApiOperation(value = "更新系统后台用户信息", notes = "更新系统后台用户信息")
    @ApiImplicitParam(name="upmsUser",value = "系统后台用户信息",dataType = "UpmsUser",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsUser upmsUser) {
        upmsUserService.updateUser(upmsUser);
        return ResponseResult.ok(upmsUser);
    }

    @ApiOperation(value = "新增系统后台用户信息", notes = "新增系统后台用户信息")
    @ApiImplicitParam(name="upmsUser",value = "系统后台用户信息",dataType = "UpmsUser",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsUser upmsUser) {
        upmsUserService.saveUser(upmsUser);
        return ResponseResult.ok(upmsUser);
    }

    @ApiOperation(value = "删除系统后台用户信息", notes = "删除系统后台用户信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{ids}")
    public ResponseResult delete(@PathVariable("ids") List<Integer> ids) {
        upmsUserService.removeByIds(ids);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "分页条件查询系统后台用户信息", notes = "分页条件查询系统后台用户信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsUser> pageQuery) {
        return ResponseResult.ok(upmsUserService.pageQuery(pageQuery));
    }

    @ApiOperation("通过用户名获取用户信息")
    @GetMapping("/loadUserByUsername/{username}")
    public Customer loadUserByUsername(@PathVariable("username") String username){
        return upmsUserService.loadUserByUsername(username);
    }

    @ApiOperation("状态修改")
    @PutMapping("/changeStatus/{id}/{status}")
    public ResponseResult changeStatus(@PathVariable("id") Integer id, @PathVariable("status") Boolean status){
        upmsUserService.changeStatus(id,status);
        return ResponseResult.ok();
    }


    @ApiOperation("锁定状态修改")
    @PutMapping("/lockStatus/{id}/{status}")
    public ResponseResult lockStatus(@PathVariable("id") Integer id, @PathVariable("status") Boolean status){
        upmsUserService.lockStatus(id,status);
        return ResponseResult.ok();
    }

    @ApiOperation("批量导入用户")
    @PostMapping("/batchImport")
    public ResponseResult batchImport(){
        return ResponseResult.ok();
    }

    @ApiOperation("用户信息导出")
    @PostMapping("/export")
    public ResponseResult export(){
        return ResponseResult.ok();
    }

    @ApiOperation("用户信息上传模板导出")
    @GetMapping("/templateExport")
    public void templateExport() throws IOException {
        upmsUserService.templateExport();
    }

}
