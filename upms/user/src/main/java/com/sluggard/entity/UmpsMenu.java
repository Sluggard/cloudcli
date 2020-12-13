package com.sluggard.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;


/**
 * <p>
    * 菜单按钮信息表
    * </p>
 *
 * @author lizheng
 * @since 2020-12-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UmpsMenu", description="菜单按钮信息")
public class UmpsMenu extends Model<UmpsMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String menuName;

    @ApiModelProperty(value = "编码")
    private String menuCode;

    @ApiModelProperty(value = "父菜单id")
    private Integer parentMenuId;

    @ApiModelProperty(value = "路由地址")
    private String menuRouter;

    @ApiModelProperty(value = "前端组件")
    private String menuComponent;

    @ApiModelProperty(value = "排序号")
    private Integer sortNo;

    @ApiModelProperty(value = "图标")
    private String menuIcon;

    @ApiModelProperty(value = "类型 menu 菜单 button 按钮")
    private String resourceType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "版本号")
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

}
