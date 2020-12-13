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
    * 职位信息表
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
@ApiModel(value="UpmsDepartment", description="职位信息")
public class UpmsDepartment extends Model<UpmsDepartment> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "职位名称")
    private String departmentName;

    @ApiModelProperty(value = "职位编码")
    private String departmentCode;

    @ApiModelProperty(value = "组织id")
    private Integer organizationId;

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
