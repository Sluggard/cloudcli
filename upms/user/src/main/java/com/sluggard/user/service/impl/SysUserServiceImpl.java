package com.sluggard.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sluggard.feign.vo.Customer;
import com.sluggard.user.entity.SysUser;
import com.sluggard.user.mapper.SysUserMapper;
import com.sluggard.user.service.SysUserService;
import com.sluggard.security.util.SecurityHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
    * 系统用户信息表 服务实现类
    * </p>
 *
 * @author lizheng
 * @since 2020-07-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getUserInfo() {
        SysUser sysUser = this.baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", SecurityHelper.getUser().getUsername()));
        if(sysUser != null){
            sysUser.setPassword(null);
            return sysUser;
        }
        return null;
    }

    @Override
    public Customer loadUserByUsername(String username) {
        SysUser sysUser = this.baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if(Objects.isNull(sysUser)){
            return null;
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(sysUser, customer);

        return customer;
    }
}
