package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsOrganizationService;
import com.sluggard.entity.UpmsOrganization;
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
    * 组织信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-13
 */
@RestController
@Api(value="UpmsOrganizationController",tags={"组织信息操作接口"})
@RequestMapping("/v1/upmsOrganization")
public class UpmsOrganizationController {

    @Autowired
    private UpmsOrganizationService upmsOrganizationService;

    @ApiOperation(value = "查询组织信息", notes = "查询组织信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsOrganizationService.getById(id));
    }

    @ApiOperation(value = "更新组织信息", notes = "更新组织信息")
    @ApiImplicitParam(name="upmsOrganization",value = "组织信息",dataType = "UpmsOrganization",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsOrganization upmsOrganization) {
        upmsOrganizationService.saveOrUpdate(upmsOrganization);
        return ResponseResult.ok(upmsOrganization);
    }

    @ApiOperation(value = "新增组织信息", notes = "新增组织信息")
    @ApiImplicitParam(name="upmsOrganization",value = "组织信息",dataType = "UpmsOrganization",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsOrganization upmsOrganization) {
        upmsOrganizationService.saveOrUpdate(upmsOrganization);
        return ResponseResult.ok(upmsOrganization);
    }

    @ApiOperation(value = "删除组织信息", notes = "删除组织信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsOrganizationService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询组织信息", notes = "分页条件查询组织信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsOrganization> pageQuery) {
        return ResponseResult.ok(upmsOrganizationService.page(pageQuery.parsePage()));
    }
}
