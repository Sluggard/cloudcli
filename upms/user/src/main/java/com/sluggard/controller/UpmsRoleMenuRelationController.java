package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsRoleMenuRelationService;
import com.sluggard.entity.UpmsRoleMenuRelation;
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
    * 角色菜单关联信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-13
 */
@RestController
@Api(value="UpmsRoleMenuRelationController",tags={"角色菜单关联信息操作接口"})
@RequestMapping("/v1/upmsRoleMenuRelation")
public class UpmsRoleMenuRelationController {

    @Autowired
    private UpmsRoleMenuRelationService upmsRoleMenuRelationService;

    @ApiOperation(value = "查询角色菜单关联信息", notes = "查询角色菜单关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsRoleMenuRelationService.getById(id));
    }

    @ApiOperation(value = "更新角色菜单关联信息", notes = "更新角色菜单关联信息")
    @ApiImplicitParam(name="upmsRoleMenuRelation",value = "角色菜单关联信息",dataType = "UpmsRoleMenuRelation",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsRoleMenuRelation upmsRoleMenuRelation) {
        upmsRoleMenuRelationService.saveOrUpdate(upmsRoleMenuRelation);
        return ResponseResult.ok(upmsRoleMenuRelation);
    }

    @ApiOperation(value = "新增角色菜单关联信息", notes = "新增角色菜单关联信息")
    @ApiImplicitParam(name="upmsRoleMenuRelation",value = "角色菜单关联信息",dataType = "UpmsRoleMenuRelation",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsRoleMenuRelation upmsRoleMenuRelation) {
        upmsRoleMenuRelationService.saveOrUpdate(upmsRoleMenuRelation);
        return ResponseResult.ok(upmsRoleMenuRelation);
    }

    @ApiOperation(value = "删除角色菜单关联信息", notes = "删除角色菜单关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsRoleMenuRelationService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询角色菜单关联信息", notes = "分页条件查询角色菜单关联信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsRoleMenuRelation> pageQuery) {
        return ResponseResult.ok(upmsRoleMenuRelationService.page(pageQuery.parsePage()));
    }
}
