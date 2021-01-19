package com.sluggard.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sluggard.entity.UpmsUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sluggard.feign.vo.Customer;
import com.sluggard.mybatis.vo.PageQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    IPage<UpmsUser> pageQuery(PageQuery<UpmsUser> pageQuery);

    /**
     * 保存用户信息
     * @param upmsUser
     */
    void saveUser(UpmsUser upmsUser);

    /**
     * 更新用户信息
     * @param upmsUser
     */
    void updateUser(UpmsUser upmsUser);

    /**
     * 改变用户状态
     * @param id
     * @param status
     */
    void changeStatus(Integer id, Boolean status);

    /**
     * 解锁/锁定用户
     * @param id
     * @param status
     */
    void lockStatus(Integer id, Boolean status);

    /**
     * 用户信息上传模板导出
     * @throws IOException
     */
    void templateExport() throws IOException;

    /**
     * 用户信息导出
     * @param upmsUser
     * @throws IOException
     */
    void export(UpmsUser upmsUser) throws IOException;

    /**
     * 用户信息导入
     * @param file
     */
    void batchImport(MultipartFile file) throws IOException;
}
