package com.sluggard.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
 * <p>
    * 
    * </p>
 *
 * @author lizheng
 * @since 2020-09-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OauthClientDetails", description="")
public class OauthClientDetails extends Model<OauthClientDetails> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "client_id", type = IdType.INPUT)
    @NotBlank(message = "clientId不能为空")
    @ApiModelProperty("客户端Id")
    private String clientId;

    @ApiModelProperty("客户端资源id")
    private String resourceIds = "oauth2-resource";

    @ApiModelProperty("客户端密钥")
    @NotBlank(message = "clientSecret不能为空")
    private String clientSecret;

    @ApiModelProperty("有效域")
    private String scope = "default";

    @NotBlank(message = "authorizedGrantTypes不能为空")
    @ApiModelProperty("授权方式")
    private String authorizedGrantTypes;

    @NotBlank(message = "webServerRedirectUri不能为空")
    @ApiModelProperty("授权成功跳转")
    private String webServerRedirectUri;

    @ApiModelProperty("权限")
    private String authorities;

    @ApiModelProperty("token有效时间")
    private Integer accessTokenValidity = 60 * 60 *2;

    @ApiModelProperty("refresh刷新时间")
    private Integer refreshTokenValidity = 60 * 60 * 12;

    @ApiModelProperty("额外信息")
    private String additionalInformation;

    @ApiModelProperty("自动通过")
    private Boolean autoapprove = false;

}
