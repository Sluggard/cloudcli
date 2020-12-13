package com.sluggard.mybatis.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.sluggard.mybatis.config.MybatisGenerateConfig;
import com.sluggard.mybatis.config.TemplateDataSourceConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizheng
 * @version V1.0
 * @Package com.homedone.user
 * @date 2020/1/10 11:35
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
public class CodeGeneratorUtils {

    private static final String TINY_INT = "tinyint";

    public static void generator(List<String> tables, boolean override, MybatisGenerateConfig mybatisGenerateConfig, TemplateDataSourceConfig templateDataSourceConfig){
        String projectPath = System.getProperty("user.dir");
        GlobalConfig config = getGlobalConfig(projectPath, override, mybatisGenerateConfig);
        DataSourceConfig dsConfig = getDataSourceConfig(templateDataSourceConfig);
        StrategyConfig stConfig = getStrategyConfig(tables.toArray(new String[]{}));
        PackageConfig pkConfig = getPackageConfig(mybatisGenerateConfig);
        InjectionConfig cfg = getInjectionConfig(projectPath, mybatisGenerateConfig);

        TemplateConfig templatesConfig = getTemplateConfig(mybatisGenerateConfig);
        AutoGenerator ag = getAutoGenerator(config, dsConfig, stConfig, pkConfig, cfg, templatesConfig);

        // 选择freemaker 作为模板
        ag.setTemplateEngine(new FreemarkerTemplateEngine());

        // 6. 执行
        ag.execute();
    }

    private static AutoGenerator getAutoGenerator(GlobalConfig config, DataSourceConfig dsConfig, StrategyConfig stConfig, PackageConfig pkConfig, InjectionConfig cfg, TemplateConfig templatesConfig) {
        // 5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
                .setCfg(cfg)
                .setTemplate(templatesConfig);
        return ag;
    }

    private static TemplateConfig getTemplateConfig(MybatisGenerateConfig mybatisGenerateConfig) {
        return new TemplateConfig()
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/templates 使用 copy
                // 至您项目 src/main/resources/templates 目录下，模板名称也可自定义如下配置：
                .setController(mybatisGenerateConfig.getControllerTemplateLocation())
                .setEntity(mybatisGenerateConfig.getEntityTemplateLocation())
                .setMapper(mybatisGenerateConfig.getMapperTemplateLocation())
                .setService(mybatisGenerateConfig.getServiceTemplateLocation())
                .setServiceImpl(mybatisGenerateConfig.getServiceImplTemplateLocation())
                .setXml(mybatisGenerateConfig.getMapperXmlTemplateLocation());
    }

    private static InjectionConfig getInjectionConfig(String projectPath, MybatisGenerateConfig mybatisGenerateConfig) {
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(mybatisGenerateConfig.getMapperXmlOutFileTemp()) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath +"/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static PackageConfig getPackageConfig(MybatisGenerateConfig mybatisGenerateConfig) {
        // 4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(mybatisGenerateConfig.getParentPackageConfig())
                //dao
                .setMapper(mybatisGenerateConfig.getMapperPackageConfig())
                //servcie
                .setService(mybatisGenerateConfig.getServicePackageConfig())
                //controller
                .setController(mybatisGenerateConfig.getControllerPackageConfig())
                // entity
                .setEntity(mybatisGenerateConfig.getEntityPackageConfig())
                .setXml(mybatisGenerateConfig.getXmlPackageConfig());
        return pkConfig;
    }

    private static StrategyConfig getStrategyConfig(String[] tables) {
        // 3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        // 自动填充字段
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill deleted = new TableFill("deleted", FieldFill.INSERT);
        TableFill version = new TableFill("version", FieldFill.INSERT);
        TableFill createdTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updatedTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        tableFillList.add(deleted);
        tableFillList.add(version);
        tableFillList.add(createdTime);
        tableFillList.add(updatedTime);
        // 全局大写命名
        stConfig.setCapitalMode(true)
                .setLogicDeleteFieldName("deleted")
                // 指定表名 字段名是否使用下划线
                //.setDbColumnUnderline(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 生成的表
                .setInclude(tables)
                .setTableFillList(tableFillList)
                .setEntityBooleanColumnRemoveIsPrefix(false)
                .setRestControllerStyle(true)
                // lombok
                .setEntityLombokModel(true);
        return stConfig;
    }

    private static DataSourceConfig getDataSourceConfig(TemplateDataSourceConfig templateDataSourceConfig) {
        // 2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
//        ResourceBundle jdbc = ResourceBundle.getBundle("jdbc");

        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName(templateDataSourceConfig.getDriverClassName())
                .setUrl(templateDataSourceConfig.getUrl())
                .setUsername(templateDataSourceConfig.getUsername())
                .setPassword(templateDataSourceConfig.getPassword())
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        if (fieldType.toLowerCase().contains(TINY_INT)) {
                            return DbColumnType.BOOLEAN;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
        return dsConfig;
    }

    private static GlobalConfig getGlobalConfig(String projectPath, boolean override, MybatisGenerateConfig mybatisGenerateConfig) {
        // 1. 全局配置
        GlobalConfig config = new GlobalConfig();
        // 是否支持AR模式
        config.setActiveRecord(true)
                // 作者
                .setAuthor(mybatisGenerateConfig.getAuthor())
                // 生成路径
                .setOutputDir(projectPath +"/src/main/java/")
                // 文件覆盖
                .setFileOverride(override)
                // 主键策略
                .setIdType(IdType.AUTO)
                // 设置生成的service接口的名字的首字母是否为I,例如IEmployeeService
                .setServiceName("%sService")
                //生成基本的resultMap
                .setBaseResultMap(true)
                //生成swagger注解
                .setSwagger2(true)
                //生成基本的SQL片段
                .setBaseColumnList(true)
                //生成后打开文件夹
                .setOpen(false).setDateType(DateType.ONLY_DATE);
        return config;
    }

}
