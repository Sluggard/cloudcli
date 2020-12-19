package com.sluggard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sluggard.entity.UpmsUser;
import com.sluggard.feign.vo.Customer;
import com.sluggard.mapper.UpmsUserMapper;
import com.sluggard.mybatis.vo.PageQuery;
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
        customer.setDisable(!upmsUser.getEnable());
        // 查询用户角色

        return customer;
    }

    @Override
    public IPage<UpmsUser> pageQuery(PageQuery<UpmsUser> pageQuery) {
        // 构造分页参数
        Page<UpmsUser> upmsUserPage = pageQuery.parsePage();
        upmsUserPage.addOrder(OrderItem.desc("create_time"));

        // 构造查询条件
        UpmsUser queryParam = pageQuery.getQueryParam();

        return this.baseMapper.selectPage(upmsUserPage,new LambdaQueryWrapper<UpmsUser>()
                .eq(Objects.nonNull(queryParam.getGender()), UpmsUser::getGender, queryParam.getGender())
                .like(StringUtils.isNotBlank(queryParam.getRealName()),
                        UpmsUser::getRealName, queryParam.getRealName())
                .like(StringUtils.isNotBlank(queryParam.getUsername()),
                        UpmsUser::getUsername, queryParam.getUsername()));
    }
}
