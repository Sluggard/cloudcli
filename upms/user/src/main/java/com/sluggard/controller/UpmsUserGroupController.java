package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsUserGroupService;
import com.sluggard.entity.UpmsUserGroup;
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
    * 用户组信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsUserGroupController",tags={"用户组信息操作接口"})
@RequestMapping("/v1/upmsUserGroup")
public class UpmsUserGroupController {

    @Autowired
    private UpmsUserGroupService upmsUserGroupService;

    @ApiOperation(value = "查询用户组信息", notes = "查询用户组信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsUserGroupService.getById(id));
    }

    @ApiOperation(value = "更新用户组信息", notes = "更新用户组信息")
    @ApiImplicitParam(name="upmsUserGroup",value = "用户组信息",dataType = "UpmsUserGroup",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsUserGroup upmsUserGroup) {
        upmsUserGroupService.saveOrUpdate(upmsUserGroup);
        return ResponseResult.ok(upmsUserGroup);
    }

    @ApiOperation(value = "新增用户组信息", notes = "新增用户组信息")
    @ApiImplicitParam(name="upmsUserGroup",value = "用户组信息",dataType = "UpmsUserGroup",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsUserGroup upmsUserGroup) {
        upmsUserGroupService.saveOrUpdate(upmsUserGroup);
        return ResponseResult.ok(upmsUserGroup);
    }

    @ApiOperation(value = "删除用户组信息", notes = "删除用户组信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsUserGroupService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询用户组信息", notes = "分页条件查询用户组信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsUserGroup> pageQuery) {
        return ResponseResult.ok(upmsUserGroupService.page(pageQuery.parsePage()));
    }
}
