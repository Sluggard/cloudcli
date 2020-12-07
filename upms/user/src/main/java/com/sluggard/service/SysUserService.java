package com.sluggard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sluggard.feign.vo.Customer;
import com.sluggard.entity.SysUser;

/**
 * <p>
    * 系统用户信息表 服务类
    * </p>
 *
 * @author lizheng
 * @since 2020-07-07
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserInfo();

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    Customer loadUserByUsername(String username);
}
