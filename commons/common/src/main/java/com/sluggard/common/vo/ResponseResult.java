package com.sluggard.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.common.vo
 * @date 2020/1/9 16:54
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ResponseResult", description="请求响应实体")
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer RESPONSE_RESULT_CODE_ERROR = 500;

    public static final Integer RESPONSE_RESULT_CODE_NO_ACCESS = 403;

    public static final Integer RESPONSE_RESULT_CODE_UNAUTHORIZED = 401;

    public static final Integer RESPONSE_RESULT_CODE_BAD_REQUEST = 400;

    public static final Integer RESPONSE_RESULT_CODE_SUCCESS = 200;

    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "错误信息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public ResponseResult(){
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> ok(){
        return new ResponseResult<T>(RESPONSE_RESULT_CODE_SUCCESS,"success", null);
    }

    public static <T> ResponseResult<T> ok(T data){
        return new ResponseResult<T>(RESPONSE_RESULT_CODE_SUCCESS,"success", data);
    }

    public static <T> ResponseResult<T> error(Integer code, String msg){
        return new ResponseResult<T>(code,msg,null);
    }

    public static <T> ResponseResult<T> error(Integer code, String msg, T data){
        return new ResponseResult<T>(code,msg,data);
    }

}
