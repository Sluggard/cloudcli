package com.sluggard.common.utls;


import com.sluggard.common.exception.ApiException;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.common.util
 * @date 2020/1/15 17:27
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class AssertHelper {


    protected AssertHelper() {
    }

    /**
     * 失败结果
     *
     * @param message 异常错误信息
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 失败断言
     *
     * @param condition
     * @param message 异常错误信息
     */
    public static void fail(boolean condition, String message) {
        if (condition) {
            fail(message);
        }
    }

}
