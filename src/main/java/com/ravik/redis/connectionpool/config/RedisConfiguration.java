package com.ravik.redis.connectionpool.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

/**
 * @author Ravi K
 *
 */

@Configuration
@Slf4j
public class RedisConfiguration {

    @Value("${spring.redis.host:localhost}")
    private String hostname;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${redis.pool.enabled:false}")
    private boolean isUseConnectionPool;

    @Value("${redis.pool.max-connection:10}")
    private int maxConnections;

    @Bean
    public LettuceClientConfiguration defaultLettuceClientConfiguration() {
        if (isUseConnectionPool) {
            log.info("Using Maximum Redis connection size {}", maxConnections);
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxTotal(maxConnections);
            return LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        }else {
            return LettuceClientConfiguration.builder().build();
        }
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
            LettuceClientConfiguration clientConfiguration) {
        log.info("Setting up standalone Redis connection factory");

        final RedisStandaloneConfiguration standaloneConfig =
                new RedisStandaloneConfiguration(hostname, port);

        final LettuceConnectionFactory factory =
                new LettuceConnectionFactory(standaloneConfig, clientConfiguration);

        // Shared connection set to false for using multiple connections.
        factory.setShareNativeConnection(!isUseConnectionPool);

        return factory;
    }

}
