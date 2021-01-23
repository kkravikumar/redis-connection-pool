package com.ravik.redis.connectionpool;

import com.ravik.redis.connectionpool.config.PoolConstants;
import com.ravik.redis.connectionpool.service.ConnectionBenchmark;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StopWatch;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @author Ravi K
 *
 */

@SpringBootApplication
@EnableCaching
@Slf4j
public class RedisConnectionPoolApplication implements CommandLineRunner {

    @Autowired
    ConnectionBenchmark benchmark;

    public static void main(String[] args) {
        SpringApplication.run(RedisConnectionPoolApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i< PoolConstants.THREAD_RUN_COUNT; i++) {
            benchmark.executeTests("execution-"+i);
        }
    }
}
