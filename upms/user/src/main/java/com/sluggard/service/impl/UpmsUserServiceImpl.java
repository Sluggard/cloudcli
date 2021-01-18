package com.sluggard.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sluggard.common.utls.AssertHelper;
import com.sluggard.entity.UpmsUser;
import com.sluggard.feign.vo.Customer;
import com.sluggard.mapper.UpmsUserMapper;
import com.sluggard.mybatis.vo.PageQuery;
import com.sluggard.service.UpmsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private HttpServletResponse response;

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

    @Override
    public void saveUser(UpmsUser upmsUser) {
        validateUser(upmsUser);
        this.baseMapper.insert(upmsUser);
    }

    @Override
    public void updateUser(UpmsUser upmsUser) {
        validateUpdateUser(upmsUser);
        this.baseMapper.updateById(upmsUser);
    }

    @Override
    public void changeStatus(Integer id, Boolean status) {
        this.baseMapper.update(new UpmsUser().setEnable(status), new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getId, id));
    }

    @Override
    public void lockStatus(Integer id, Boolean status) {
        this.baseMapper.update(new UpmsUser().setLocked(status), new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getId, id));
    }

    @Override
    public void templateExport() throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UpmsUser.class)
                .head(UpmsUser.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("用户信息导入模板")
                .sheetNo(0).doWrite(this.list());
    }

    public void validateUser(UpmsUser upmsUser){
        UpmsUser user = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>()
                .eq(UpmsUser::getUsername, upmsUser.getUsername()));
        AssertHelper.fail(Objects.nonNull(user), "用户名已存在");

        user = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getIdNo, upmsUser.getIdNo()));
        AssertHelper.fail(Objects.nonNull(user), "身份证已绑定");
    }

    public void validateUpdateUser(UpmsUser upmsUser){
        UpmsUser user = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>()
                .eq(UpmsUser::getUsername, upmsUser.getUsername())
                .ne(UpmsUser::getId, upmsUser.getId()));
        AssertHelper.fail(Objects.nonNull(user), "用户名已存在");

        user = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>()
                .eq(UpmsUser::getIdNo, upmsUser.getIdNo())
                .ne(UpmsUser::getId, upmsUser.getId()));
        AssertHelper.fail(Objects.nonNull(user), "身份证已绑定");
    }

}
