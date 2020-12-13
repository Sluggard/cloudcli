package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsRoleService;
import com.sluggard.entity.UpmsRole;
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
    * 角色信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-13
 */
@RestController
@Api(value="UpmsRoleController",tags={"角色信息操作接口"})
@RequestMapping("/v1/upmsRole")
public class UpmsRoleController {

    @Autowired
    private UpmsRoleService upmsRoleService;

    @ApiOperation(value = "查询角色信息", notes = "查询角色信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsRoleService.getById(id));
    }

    @ApiOperation(value = "更新角色信息", notes = "更新角色信息")
    @ApiImplicitParam(name="upmsRole",value = "角色信息",dataType = "UpmsRole",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsRole upmsRole) {
        upmsRoleService.saveOrUpdate(upmsRole);
        return ResponseResult.ok(upmsRole);
    }

    @ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    @ApiImplicitParam(name="upmsRole",value = "角色信息",dataType = "UpmsRole",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsRole upmsRole) {
        upmsRoleService.saveOrUpdate(upmsRole);
        return ResponseResult.ok(upmsRole);
    }

    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsRoleService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询角色信息", notes = "分页条件查询角色信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsRole> pageQuery) {
        return ResponseResult.ok(upmsRoleService.page(pageQuery.parsePage()));
    }
}
