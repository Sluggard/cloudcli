package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsOrganizationRoleRelationService;
import com.sluggard.entity.UpmsOrganizationRoleRelation;
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
    * 组织角色关联信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-13
 */
@RestController
@Api(value="UpmsOrganizationRoleRelationController",tags={"组织角色关联信息操作接口"})
@RequestMapping("/v1/upmsOrganizationRoleRelation")
public class UpmsOrganizationRoleRelationController {

    @Autowired
    private UpmsOrganizationRoleRelationService upmsOrganizationRoleRelationService;

    @ApiOperation(value = "查询组织角色关联信息", notes = "查询组织角色关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsOrganizationRoleRelationService.getById(id));
    }

    @ApiOperation(value = "更新组织角色关联信息", notes = "更新组织角色关联信息")
    @ApiImplicitParam(name="upmsOrganizationRoleRelation",value = "组织角色关联信息",dataType = "UpmsOrganizationRoleRelation",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsOrganizationRoleRelation upmsOrganizationRoleRelation) {
        upmsOrganizationRoleRelationService.saveOrUpdate(upmsOrganizationRoleRelation);
        return ResponseResult.ok(upmsOrganizationRoleRelation);
    }

    @ApiOperation(value = "新增组织角色关联信息", notes = "新增组织角色关联信息")
    @ApiImplicitParam(name="upmsOrganizationRoleRelation",value = "组织角色关联信息",dataType = "UpmsOrganizationRoleRelation",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsOrganizationRoleRelation upmsOrganizationRoleRelation) {
        upmsOrganizationRoleRelationService.saveOrUpdate(upmsOrganizationRoleRelation);
        return ResponseResult.ok(upmsOrganizationRoleRelation);
    }

    @ApiOperation(value = "删除组织角色关联信息", notes = "删除组织角色关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsOrganizationRoleRelationService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询组织角色关联信息", notes = "分页条件查询组织角色关联信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsOrganizationRoleRelation> pageQuery) {
        return ResponseResult.ok(upmsOrganizationRoleRelationService.page(pageQuery.parsePage()));
    }
}
