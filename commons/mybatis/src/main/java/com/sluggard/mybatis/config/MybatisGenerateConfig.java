package com.sluggard.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lizheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mybatis-plus.configuration.code-generation")
public class MybatisGenerateConfig {

    private String author = System.getProperty("user.name");
    private String controllerTemplateLocation = "templates/controller.java";
    private String serviceTemplateLocation = "templates/service.java";
    private String serviceImplTemplateLocation = "templates/serviceImpl.java";
    private String entityTemplateLocation = "templates/entity.java";
    private String mapperTemplateLocation = "templates/mapper.java";
    private String mapperXmlTemplateLocation;
    private String mapperXmlOutFileTemp = "/templates/mapper.xml.ftl";

    private String parentPackageConfig;
    private String servicePackageConfig = "service";
    private String mapperPackageConfig = "mapper";
    private String controllerPackageConfig = "controller";
    private String entityPackageConfig = "entity";
    private String xmlPackageConfig = "";
}
