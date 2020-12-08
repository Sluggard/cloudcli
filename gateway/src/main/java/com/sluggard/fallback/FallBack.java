package com.sluggard.fallback;


import com.sluggard.common.vo.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.gateway.fallback
 * @date 2020/1/9 15:25
 * @Copyright © 2019-2021
 */
@RestController
@RequestMapping("/fallback")
public class FallBack {

    @RequestMapping("")
    public ResponseResult fallback(){
        return  ResponseResult.error(ResponseResult.RESPONSE_RESULT_SERVER_DOWN, "服务暂不可用！");
    }
}
