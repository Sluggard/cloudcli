package com.sluggard.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sluggard.convert.Boolean2StringConverter;
import com.sluggard.convert.Gender2StringConverter;
import com.sluggard.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <p>
    * 系统后台用户信息表
    * </p>
 *
 * @author lizheng
 * @since 2020-12-14
 */
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(12)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UpmsUser", description="系统后台用户信息")
public class UpmsUser extends Model<UpmsUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty("用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @ExcelIgnore
    private String password;

    @ApiModelProperty(value = "头像")
    @ExcelIgnore
    private String headPortrait;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty("姓名")
    private String realName;

    @ApiModelProperty(value = "性别")
    @ExcelProperty(value = "性别",converter = Gender2StringConverter.class)
    private Gender gender;

    @ApiModelProperty(value = "年龄")
    @ExcelProperty("年龄")
    private Integer age;

    @ApiModelProperty(value = "身份证号码")
    @ExcelProperty("身份证号码")
    @ColumnWidth(20)
    private String idNo;

    @ApiModelProperty(value = "电话号码")
    @ExcelProperty("电话号码")
    @ColumnWidth(15)
    private String telephone;

    @DateTimeFormat("yyyy年MM月dd日")
    @ApiModelProperty(value = "生日")
    @ExcelProperty("生日")
    @ColumnWidth(20)
    private Date birthDay;


    @ApiModelProperty(value = "启用/禁用")
    @ExcelProperty(value = "启用/禁用",converter = Boolean2StringConverter.class)
    private Boolean enable;

    @ApiModelProperty(value = "锁定")
    @ExcelProperty(value = "锁定",converter = Boolean2StringConverter.class)
    private Boolean locked;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    @ColumnWidth(25)
    private Date createTime;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("更新时间")
    @ColumnWidth(25)
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    @ExcelProperty("备注")
    private String remark;

    @ApiModelProperty(value = "版本号")
    @TableField(fill = FieldFill.INSERT)
    @Version
    @ExcelIgnore
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @ExcelIgnore
    private Boolean deleted;

}
