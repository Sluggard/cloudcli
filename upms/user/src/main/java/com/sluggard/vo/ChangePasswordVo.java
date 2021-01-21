package com.sluggard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2021/1/21 14:53
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Data
@ApiModel(description = "修改用户密码")
public class ChangePasswordVo implements Serializable {

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;

    @ApiModelProperty(value = "验证码")
    private String checkCode;

    @ApiModelProperty(value = "验证码key")
    private String checkCodePrefix;

    @ApiModelProperty(value = "id")
    private Integer id;

}
