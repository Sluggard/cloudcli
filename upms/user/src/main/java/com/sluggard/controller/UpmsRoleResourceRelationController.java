package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsRoleResourceRelationService;
import com.sluggard.entity.UpmsRoleResourceRelation;
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
    * 角色资源关联信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsRoleResourceRelationController",tags={"角色资源关联信息操作接口"})
@RequestMapping("/v1/upmsRoleResourceRelation")
public class UpmsRoleResourceRelationController {

    @Autowired
    private UpmsRoleResourceRelationService upmsRoleResourceRelationService;

    @ApiOperation(value = "查询角色资源关联信息", notes = "查询角色资源关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsRoleResourceRelationService.getById(id));
    }

    @ApiOperation(value = "更新角色资源关联信息", notes = "更新角色资源关联信息")
    @ApiImplicitParam(name="upmsRoleResourceRelation",value = "角色资源关联信息",dataType = "UpmsRoleResourceRelation",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsRoleResourceRelation upmsRoleResourceRelation) {
        upmsRoleResourceRelationService.saveOrUpdate(upmsRoleResourceRelation);
        return ResponseResult.ok(upmsRoleResourceRelation);
    }

    @ApiOperation(value = "新增角色资源关联信息", notes = "新增角色资源关联信息")
    @ApiImplicitParam(name="upmsRoleResourceRelation",value = "角色资源关联信息",dataType = "UpmsRoleResourceRelation",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsRoleResourceRelation upmsRoleResourceRelation) {
        upmsRoleResourceRelationService.saveOrUpdate(upmsRoleResourceRelation);
        return ResponseResult.ok(upmsRoleResourceRelation);
    }

    @ApiOperation(value = "删除角色资源关联信息", notes = "删除角色资源关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsRoleResourceRelationService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询角色资源关联信息", notes = "分页条件查询角色资源关联信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsRoleResourceRelation> pageQuery) {
        return ResponseResult.ok(upmsRoleResourceRelationService.page(pageQuery.parsePage()));
    }
}
