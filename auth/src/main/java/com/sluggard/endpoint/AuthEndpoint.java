package com.sluggard.endpoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.sluggard.entity.OauthClientDetails;
import com.sluggard.service.OauthClientDetailsService;
import com.sluggard.common.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/17 11:04
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@AllArgsConstructor
@RestController
@Api(value="AuthEndpoint",tags={"客户端接口"})
public class AuthEndpoint {

    private final KeyPair keyPair;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @ApiOperation("注册客户端")
    @PostMapping("register")
    public ResponseResult<String> registerClient(@Valid @RequestBody  OauthClientDetails oauthClientDetails){
        oauthClientDetailsService.register(oauthClientDetails);
        return ResponseResult.ok();
    }

    @ApiOperation("获取")
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
