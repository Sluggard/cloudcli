package com.sluggard.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/1 15:40
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021
 */
@Configuration
@ConditionalOnClass(RedissonConnectionFactory.class)
public class RedisConfig {

    private static final String HEAD = "redis://";

    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.useMasterSlaveServers()
                .setMasterAddress(HEAD + host + ":" + port)
                .setDatabase(database)
                .setPassword(password).setReadMode(ReadMode.MASTER_SLAVE);
        return Redisson.create(config);
    }


}
