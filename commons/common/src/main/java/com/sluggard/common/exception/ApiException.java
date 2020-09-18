package com.sluggard.common.exception;

import lombok.Data;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.common.exception
 * @date 2020/1/14 12:24
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Data
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -222041695616255265L;


    private String message;


    public ApiException(String message){
        super(message);
        this.message = message;
    }

}
