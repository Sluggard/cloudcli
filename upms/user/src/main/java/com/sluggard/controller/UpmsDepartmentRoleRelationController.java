package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsDepartmentRoleRelationService;
import com.sluggard.entity.UpmsDepartmentRoleRelation;
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
    * 职位角色关联信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsDepartmentRoleRelationController",tags={"职位角色关联信息操作接口"})
@RequestMapping("/v1/upmsDepartmentRoleRelation")
public class UpmsDepartmentRoleRelationController {

    @Autowired
    private UpmsDepartmentRoleRelationService upmsDepartmentRoleRelationService;

    @ApiOperation(value = "查询职位角色关联信息", notes = "查询职位角色关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsDepartmentRoleRelationService.getById(id));
    }

    @ApiOperation(value = "更新职位角色关联信息", notes = "更新职位角色关联信息")
    @ApiImplicitParam(name="upmsDepartmentRoleRelation",value = "职位角色关联信息",dataType = "UpmsDepartmentRoleRelation",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsDepartmentRoleRelation upmsDepartmentRoleRelation) {
        upmsDepartmentRoleRelationService.saveOrUpdate(upmsDepartmentRoleRelation);
        return ResponseResult.ok(upmsDepartmentRoleRelation);
    }

    @ApiOperation(value = "新增职位角色关联信息", notes = "新增职位角色关联信息")
    @ApiImplicitParam(name="upmsDepartmentRoleRelation",value = "职位角色关联信息",dataType = "UpmsDepartmentRoleRelation",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsDepartmentRoleRelation upmsDepartmentRoleRelation) {
        upmsDepartmentRoleRelationService.saveOrUpdate(upmsDepartmentRoleRelation);
        return ResponseResult.ok(upmsDepartmentRoleRelation);
    }

    @ApiOperation(value = "删除职位角色关联信息", notes = "删除职位角色关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsDepartmentRoleRelationService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询职位角色关联信息", notes = "分页条件查询职位角色关联信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsDepartmentRoleRelation> pageQuery) {
        return ResponseResult.ok(upmsDepartmentRoleRelationService.page(pageQuery.parsePage()));
    }
}
