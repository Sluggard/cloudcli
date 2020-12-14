package com.sluggard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sluggard.entity.UpmsUser;
import com.sluggard.feign.vo.Customer;
import com.sluggard.mapper.UpmsUserMapper;
import com.sluggard.service.UpmsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
    * 系统后台用户信息表 服务实现类
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@Service
public class UpmsUserServiceImpl extends ServiceImpl<UpmsUserMapper, UpmsUser> implements UpmsUserService {

    @Override
    public Customer loadUserByUsername(String username) {
        UpmsUser upmsUser = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getUsername, username));
        if(Objects.isNull(upmsUser)){
            return null;
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(upmsUser, customer);

        // 查询用户角色

        return customer;
    }
}
