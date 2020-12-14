package com.sluggard.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import com.sluggard.common.vo.ResponseResult;
import com.sluggard.mybatis.vo.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.sluggard.service.UmpsMenuService;
import com.sluggard.entity.UmpsMenu;
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
    * 菜单按钮信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@RestController
@Api(value="UmpsMenuController",tags={"菜单按钮信息操作接口"})
@RequestMapping("/v1/umpsMenu")
public class UmpsMenuController {

    @Autowired
    private UmpsMenuService umpsMenuService;

    @ApiOperation(value = "查询菜单按钮信息", notes = "查询菜单按钮信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @GetMapping("get/{id}")
    public ResponseResult get(@PathVariable Integer id) {
        return ResponseResult.ok(umpsMenuService.getById(id));
    }

    @ApiOperation(value = "更新菜单按钮信息", notes = "更新菜单按钮信息")
    @ApiImplicitParam(name="umpsMenu",value = "菜单按钮信息",dataType = "UmpsMenu",required=true)
    @PutMapping("update")
    public ResponseResult update(@RequestBody UmpsMenu umpsMenu) {
        umpsMenuService.saveOrUpdate(umpsMenu);
        return ResponseResult.ok(umpsMenu);
    }

    @ApiOperation(value = "新增菜单按钮信息", notes = "新增菜单按钮信息")
    @ApiImplicitParam(name="umpsMenu",value = "菜单按钮信息",dataType = "UmpsMenu",required=true)
    @PostMapping("save")
    public ResponseResult save(@RequestBody UmpsMenu umpsMenu) {
        umpsMenuService.saveOrUpdate(umpsMenu);
        return ResponseResult.ok(umpsMenu);
    }

    @ApiOperation(value = "删除菜单按钮信息", notes = "删除菜单按钮信息")
    @ApiImplicitParam(name="id",value = "主键id",dataType = "int",required=true)
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.ok(umpsMenuService.removeById(id));
    }

    @ApiOperation(value = "分页条件查询菜单按钮信息", notes = "分页条件查询菜单按钮信息")
    @PostMapping("pageQuery")
    public ResponseResult pageQuery(@RequestBody PageQuery<UmpsMenu> pageQuery) {
        return ResponseResult.ok(umpsMenuService.page(pageQuery.parsePage()));
    }
}
