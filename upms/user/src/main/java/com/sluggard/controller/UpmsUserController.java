package com.sluggard.controller;


import com.sluggard.feign.vo.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsUserService;
import com.sluggard.entity.UpmsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
        upmsUserService.saveOrUpdate(upmsUser);
        return ResponseResult.ok(upmsUser);
    }

    @ApiOperation(value = "新增系统后台用户信息", notes = "新增系统后台用户信息")
    @ApiImplicitParam(name="upmsUser",value = "系统后台用户信息",dataType = "UpmsUser",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsUser upmsUser) {
        upmsUserService.saveOrUpdate(upmsUser);
        return ResponseResult.ok(upmsUser);
    }

    @ApiOperation(value = "删除系统后台用户信息", notes = "删除系统后台用户信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsUserService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询系统后台用户信息", notes = "分页条件查询系统后台用户信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsUser> pageQuery) {
        return ResponseResult.ok(upmsUserService.page(pageQuery.parsePage()));
    }

    @ApiOperation("通过用户名获取用户信息")
    @GetMapping("/loadUserByUsername/{username}")
    public Customer loadUserByUsername(@PathVariable("username") String username){
        return upmsUserService.loadUserByUsername(username);
    }
}
