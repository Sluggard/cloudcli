package com.sluggard.endpoint;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.sluggard.entity.OauthClientDetails;
import com.sluggard.service.OauthClientDetailsService;
import com.sluggard.common.vo.ResponseResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/17 11:04
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021
 */
@Slf4j
@AllArgsConstructor
@Controller
@Api(value="AuthEndpoint",tags={"客户端接口"})
public class AuthEndpoint {

    private final KeyPair keyPair;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private DefaultKaptcha producer;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("注册客户端")
    @PostMapping("register")
    @ResponseBody
    public ResponseResult<String> registerClient(@Valid @RequestBody  OauthClientDetails oauthClientDetails){
        oauthClientDetailsService.register(oauthClientDetails);
        return ResponseResult.ok();
    }

    @ApiOperation("获取")
    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    @RequestMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView, HttpServletRequest request){
        modelAndView.addObject("username", request.getParameter("username"));
        modelAndView.addObject("password", request.getParameter("password"));
        modelAndView.addObject("msg", request.getAttribute("error"));
        modelAndView.setViewName("auth-login");
        return modelAndView;
    }



    @ApiOperation("获取验证码接口")
    @GetMapping("/captcha")
    @ResponseBody
    public ResponseResult captcha()throws IOException {
        /**
         * 前后端分离 登录验证码 方案
         * 后端生成图片 验证码字符串 uuid
         * uuid为key  验证码字符串为value
         * 传递bs64图片 和uuid给前端
         * 前端在登录的时候 传递 账号 密码 验证码 uuid
         * 通过uuid获取 验证码 验证
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //获取验证码
        String text = producer.createText();
        log.info("登录验证码："+text);

        BufferedImage image = producer.createImage(text);
        ImageIO.write(image, "png", out);
        String base64bytes = Base64.encode(out.toByteArray());

        //该字符串传输至前端放入src即可显示图片，安卓可以去掉data:image/png;base64,
        String src = "data:image/png;base64," + base64bytes;

        String checkCodePrefix = UUID.randomUUID().toString();
        Map<String, Object> map = new HashMap<>(2);
        map.put("checkCodePrefix", checkCodePrefix);
        map.put("img", src);
        stringRedisTemplate.opsForValue().set(checkCodePrefix, text, 5, TimeUnit.MINUTES);
        return ResponseResult.ok(map);
    }

}
