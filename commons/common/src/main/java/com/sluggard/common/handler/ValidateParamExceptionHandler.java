package com.sluggard.common.handler;


import com.sluggard.common.exception.ApiException;
import com.sluggard.common.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author lizheng
 * @version V1.0
 * @Description:
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@ControllerAdvice("com.sluggard")
@ResponseBody
@Slf4j
public class ValidateParamExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult otherExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseResult apiExceptionHandler(ApiException ex) {
        log.error(ex.getMessage());
        return ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_BAD_REQUEST, ex.getMessage());
    }

    /**
     * 处理不带任何注解的参数绑定校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<?> handleBingException(BindException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> ((FieldError)objectError).getField() + ((FieldError)objectError).getDefaultMessage())
                .collect(Collectors.joining(","));
        //"errorMsg": "name不能为空,age最小不能小于18"
        return ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_BAD_REQUEST, errorMsg);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult MethodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        //按需重新封装需要返回的错误信息
        //解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
        String errorMessage = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst().orElse(null)
                .getDefaultMessage();

        return ResponseResult.error(ResponseResult.RESPONSE_RESULT_CODE_BAD_REQUEST, errorMessage);
    }

}
