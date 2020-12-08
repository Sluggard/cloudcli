package com.sluggard.swagger.config;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.user.config
 * @date 2020/1/10 10:08
 * @Copyright © 2019-2021
 */
@Configuration
@Data
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.application.name:default}")
    private String applicationName;

    private String title = "文档中心";
    private String desc = "基于springBoot编写的RESful-API";
    private String version = "v1";
    private String termsOfServiceUrl = "lb://";
    private String license = "Apache 2.0";
    private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.html";
    private String basePackage = "com.sluggard";
    private String groupName = "";
    private String contactName = "lizheng";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(desc)
                .version(version)
                .termsOfServiceUrl(termsOfServiceUrl + applicationName)
                .licenseUrl(licenseUrl)
                .license(license)
                .contact(new Contact(contactName, "https://github.com/JustLikeLee", "18306079883@163.com"))
                .build();
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(applicationName + groupName)
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, customerResponseMessage())
                .globalResponseMessage(RequestMethod.GET, customerResponseMessage())
                .globalResponseMessage(RequestMethod.PUT, customerResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, customerResponseMessage())
                .forCodeGeneration(true).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
                .build();
    }

    private List<ResponseMessage> customerResponseMessage() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        list.add(new ResponseMessageBuilder().code(400).message("请求失败,具体查看返回业务状态码与对应消息").build());
        list.add(new ResponseMessageBuilder().code(401).message("请求失败,没有访问权限").build());
        list.add(new ResponseMessageBuilder().code(500).message("系统繁忙，请稍后再试").build());
        list.add(new ResponseMessageBuilder().code(503).message("服务暂不可用").build());
        list.add(new ResponseMessageBuilder().code(500000).message("业务异常").build());
        list.add(new ResponseMessageBuilder().code(500001).message("参数异常").build());
        list.add(new ResponseMessageBuilder().code(500601).message("验证码错误").build());
        list.add(new ResponseMessageBuilder().code(500301).message("获取验证码间隔时间过短，请稍后再试").build());
        list.add(new ResponseMessageBuilder().code(500303).message("短信发送异常").build());
        return list;
    }
}
