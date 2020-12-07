package com.sluggard.controller;


import com.sluggard.feign.vo.Customer;
import com.sluggard.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    private PasswordEncoder passwordEncoder;

    @ApiOperation("通过用户名获取用户信息")
    @GetMapping("/loadUserByUsername/{username}")
    public Customer loadUserByUsername(@PathVariable("username") String username){
        return sysUserService.loadUserByUsername(username);
    }

}
