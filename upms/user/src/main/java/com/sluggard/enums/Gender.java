package com.sluggard.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/12/16 20:04
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public enum Gender  {

    MAN("男"),
    FEMALE("女"),
    SECRET("保密"),
    ;

    @Setter
    @Getter
    private String desc;

    Gender(String desc) {
        this.desc = desc;
    }


}
