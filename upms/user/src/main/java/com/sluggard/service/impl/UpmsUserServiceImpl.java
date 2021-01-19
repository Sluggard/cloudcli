package com.sluggard.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sluggard.common.utls.AssertHelper;
import com.sluggard.entity.UpmsUser;
import com.sluggard.feign.vo.Customer;
import com.sluggard.mapper.UpmsUserMapper;
import com.sluggard.mybatis.vo.PageQuery;
import com.sluggard.service.UpmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 系统后台用户信息表 服务实现类
 * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@Slf4j
@Service
public class UpmsUserServiceImpl extends ServiceImpl<UpmsUserMapper, UpmsUser> implements UpmsUserService {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer loadUserByUsername(String username) {
        UpmsUser upmsUser = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getUsername, username));
        if (Objects.isNull(upmsUser)) {
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

        return this.baseMapper.selectPage(upmsUserPage, new LambdaQueryWrapper<UpmsUser>()
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
        UpmsUser upmsUser = new UpmsUser();
        upmsUser.setEnable(status);
        this.baseMapper.update(upmsUser, new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getId, id));
    }

    @Override
    public void lockStatus(Integer id, Boolean status) {
        UpmsUser upmsUser = new UpmsUser();
        upmsUser.setLocked(status);
        this.baseMapper.update(upmsUser, new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getId, id));
    }

    @Override
    public void templateExport() throws IOException {
        setResponseHeader("用户信息导入模板");
        EasyExcel.write(response.getOutputStream(), UpmsUser.class)
                .head(UpmsUser.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("用户信息导入模板")
                .sheetNo(0)
                .doWrite(Collections.EMPTY_LIST);
    }

    @Override
    public void export(UpmsUser upmsUser) throws IOException {
        List<UpmsUser> list = this.list(new LambdaQueryWrapper<UpmsUser>()
                .orderByDesc(UpmsUser::getUpdateTime)
                .like(StringUtils.isNotBlank(upmsUser.getUsername()), UpmsUser::getUsername, upmsUser.getUsername())
                .eq(StringUtils.isNotBlank(upmsUser.getRealName()), UpmsUser::getRealName, upmsUser.getRealName())
                .eq(Objects.nonNull(upmsUser.getGender()), UpmsUser::getGender, upmsUser.getGender()));

        setResponseHeader("用户信息统计");
        EasyExcel.write(response.getOutputStream(), UpmsUser.class)
                .head(UpmsUser.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("用户信息统计")
                .sheetNo(0)
                .doWrite(list);
    }

    @Override
    public void batchImport(MultipartFile file) throws IOException {
        File tmpFile = null;
        try {
            UpmsUserService upmsUserService = this;
            tmpFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(tmpFile.getAbsoluteFile());
            EasyExcel.read(tmpFile.getAbsoluteFile(), UpmsUser.class, new AnalysisEventListener<UpmsUser>() {
                final List<UpmsUser> list = new ArrayList<>();

                @Override
                public void invoke(UpmsUser upmsUser, AnalysisContext analysisContext) {
                    upmsUser.setPassword(passwordEncoder.encode("123456"));
                    list.add(upmsUser);
                    if (list.size() > 3000) {
                        upmsUserService.saveBatch(list);
                        list.clear();
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    upmsUserService.saveBatch(list);
                    log.info("数据解析完成");
                }
            })
                    .sheet()
                    .headRowNumber(1)
                    .doRead();
        }finally {
            FileUtils.deleteQuietly(tmpFile);
        }
    }

    private void setResponseHeader(String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
    }

    public void validateUser(UpmsUser upmsUser) {
        UpmsUser user = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>()
                .eq(UpmsUser::getUsername, upmsUser.getUsername()));
        AssertHelper.fail(Objects.nonNull(user), "用户名已存在");

        user = this.baseMapper.selectOne(new LambdaQueryWrapper<UpmsUser>().eq(UpmsUser::getIdNo, upmsUser.getIdNo()));
        AssertHelper.fail(Objects.nonNull(user), "身份证已绑定");
    }

    public void validateUpdateUser(UpmsUser upmsUser) {
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
