package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsDepartmentService;
import com.sluggard.entity.UpmsDepartment;
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
    * 职位信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsDepartmentController",tags={"职位信息操作接口"})
@RequestMapping("/v1/upmsDepartment")
public class UpmsDepartmentController {

    @Autowired
    private UpmsDepartmentService upmsDepartmentService;

    @ApiOperation(value = "查询职位信息", notes = "查询职位信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsDepartmentService.getById(id));
    }

    @ApiOperation(value = "更新职位信息", notes = "更新职位信息")
    @ApiImplicitParam(name="upmsDepartment",value = "职位信息",dataType = "UpmsDepartment",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsDepartment upmsDepartment) {
        upmsDepartmentService.saveOrUpdate(upmsDepartment);
        return ResponseResult.ok(upmsDepartment);
    }

    @ApiOperation(value = "新增职位信息", notes = "新增职位信息")
    @ApiImplicitParam(name="upmsDepartment",value = "职位信息",dataType = "UpmsDepartment",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsDepartment upmsDepartment) {
        upmsDepartmentService.saveOrUpdate(upmsDepartment);
        return ResponseResult.ok(upmsDepartment);
    }

    @ApiOperation(value = "删除职位信息", notes = "删除职位信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsDepartmentService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询职位信息", notes = "分页条件查询职位信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsDepartment> pageQuery) {
        return ResponseResult.ok(upmsDepartmentService.page(pageQuery.parsePage()));
    }
}
