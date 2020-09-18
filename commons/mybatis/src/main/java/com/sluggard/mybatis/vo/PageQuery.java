package com.sluggard.mybatis.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.common.vo
 * @date 2020/1/9 16:54
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Data
@ApiModel(value="PageQuery", description="条件查询实体")
public class PageQuery<T> implements Serializable {

    @ApiModelProperty(value = "当前页码", required = true)
    private Integer current = 1;

    @ApiModelProperty(value = "分页大小",required = true)
    private Integer pageSize = 10;

    @ApiModelProperty(value = "条件参数")
    private T queryParam;

    /**
     * 分页查询
     * @return Page
     */
    public Page<T> parsePage(){
        Page<T> page = new Page<>();
        page.setCurrent(current);
        page.setSize(pageSize);
        return page;
    }


}
