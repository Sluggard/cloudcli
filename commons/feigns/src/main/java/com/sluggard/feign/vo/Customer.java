package com.sluggard.feign.vo;

import lombok.Data;

import java.util.List;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/17 16:17
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Data
public class Customer {

    private String username;

    private String password;

    private Boolean locked;

    private Boolean disable;

    private String[] roles = new String[]{};

}
