package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UpmsResourceService;
import com.sluggard.entity.UpmsResource;
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
    * 资源信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UpmsResourceController",tags={"资源信息操作接口"})
@RequestMapping("/v1/upmsResource")
public class UpmsResourceController {

    @Autowired
    private UpmsResourceService upmsResourceService;

    @ApiOperation(value = "查询资源信息", notes = "查询资源信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(upmsResourceService.getById(id));
    }

    @ApiOperation(value = "更新资源信息", notes = "更新资源信息")
    @ApiImplicitParam(name="upmsResource",value = "资源信息",dataType = "UpmsResource",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UpmsResource upmsResource) {
        upmsResourceService.saveOrUpdate(upmsResource);
        return ResponseResult.ok(upmsResource);
    }

    @ApiOperation(value = "新增资源信息", notes = "新增资源信息")
    @ApiImplicitParam(name="upmsResource",value = "资源信息",dataType = "UpmsResource",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UpmsResource upmsResource) {
        upmsResourceService.saveOrUpdate(upmsResource);
        return ResponseResult.ok(upmsResource);
    }

    @ApiOperation(value = "删除资源信息", notes = "删除资源信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(upmsResourceService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询资源信息", notes = "分页条件查询资源信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UpmsResource> pageQuery) {
        return ResponseResult.ok(upmsResourceService.page(pageQuery.parsePage()));
    }
}
