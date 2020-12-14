package com.sluggard.service;

import com.sluggard.entity.UpmsUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sluggard.feign.vo.Customer;

/**
 * <p>
    * 系统后台用户信息表 服务类
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
public interface UpmsUserService extends IService<UpmsUser> {

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    Customer loadUserByUsername(String username);
}
