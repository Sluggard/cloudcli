package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsUserDepartmentRelationService;
import com.sluggard.entity.UpmsUserDepartmentRelation;
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
    * 用户职位关联信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsUserDepartmentRelationController",tags={"用户职位关联信息操作接口"})
@RequestMapping("/v1/upmsUserDepartmentRelation")
public class UpmsUserDepartmentRelationController {

    @Autowired
    private UpmsUserDepartmentRelationService upmsUserDepartmentRelationService;

    @ApiOperation(value = "查询用户职位关联信息", notes = "查询用户职位关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsUserDepartmentRelationService.getById(id));
    }

    @ApiOperation(value = "更新用户职位关联信息", notes = "更新用户职位关联信息")
    @ApiImplicitParam(name="upmsUserDepartmentRelation",value = "用户职位关联信息",dataType = "UpmsUserDepartmentRelation",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsUserDepartmentRelation upmsUserDepartmentRelation) {
        upmsUserDepartmentRelationService.saveOrUpdate(upmsUserDepartmentRelation);
        return ResponseResult.ok(upmsUserDepartmentRelation);
    }

    @ApiOperation(value = "新增用户职位关联信息", notes = "新增用户职位关联信息")
    @ApiImplicitParam(name="upmsUserDepartmentRelation",value = "用户职位关联信息",dataType = "UpmsUserDepartmentRelation",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsUserDepartmentRelation upmsUserDepartmentRelation) {
        upmsUserDepartmentRelationService.saveOrUpdate(upmsUserDepartmentRelation);
        return ResponseResult.ok(upmsUserDepartmentRelation);
    }

    @ApiOperation(value = "删除用户职位关联信息", notes = "删除用户职位关联信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsUserDepartmentRelationService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询用户职位关联信息", notes = "分页条件查询用户职位关联信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsUserDepartmentRelation> pageQuery) {
        return ResponseResult.ok(upmsUserDepartmentRelationService.page(pageQuery.parsePage()));
    }
}