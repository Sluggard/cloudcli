package com.sluggard.controller;


import com.sluggard.feign.vo.Customer;
import com.sluggard.mybatis.config.MybatisGenerateConfig;
import com.sluggard.mybatis.config.TemplateDataSourceConfig;
import com.sluggard.mybatis.util.CodeGeneratorUtils;
import com.sluggard.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * <p>
    * 系统用户信息表 前端控制器
    * </p>
 *
 * @author lizheng
 * @since 2020-07-07
 */
@RestController
@Api(value="SysUserController",tags={"系统用户信息操作接口"})
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MybatisGenerateConfig mybatisGenerateConfig;

    @Autowired
    private TemplateDataSourceConfig templateDataSourceConfig;

    @ApiOperation("通过用户名获取用户信息")
    @GetMapping("/loadUserByUsername/{username}")
    public Customer loadUserByUsername(@PathVariable("username") String username){
        return sysUserService.loadUserByUsername(username);
    }


    @ApiOperation("代码生成")
    @GetMapping("generator/{tables}")
    public void generator(@PathVariable("tables") String[] tables){
        CodeGeneratorUtils.generator(Arrays.asList(tables) ,false, mybatisGenerateConfig,templateDataSourceConfig);
    }
}
