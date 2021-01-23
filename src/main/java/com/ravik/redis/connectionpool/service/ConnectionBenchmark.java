package com.ravik.redis.connectionpool.service;

import com.ravik.redis.connectionpool.config.PoolConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.Executor;

@Service
@Slf4j
@EnableAsync
public class ConnectionBenchmark {
    private final RedisReadWriteService readWriteService;

    @Value("${redis.read.keys:50}")
    private int readOperationCount;

    @Value("${redis.read.keys:50}")
    private int writeOperationCount;

    @Autowired
    public ConnectionBenchmark(RedisReadWriteService readWriteService){
        this.readWriteService = readWriteService;
    }

    @Async
    public void executeTests(String keySalt) {
        StopWatch watch = new StopWatch("Redis Operation BenchMark " + keySalt);
        watch.start(keySalt);

        executeWriteOperation(keySalt);
        executeReadOperation(keySalt);

        watch.stop();
        log.info(watch.shortSummary());

    }

    private void executeReadOperation(String keySalt) {
        log.info("Implement read operation {} times.", readOperationCount);
        StopWatch watch = new StopWatch("Redis Read");
        watch.start("Read");
        for(int i = 0; i < readOperationCount; i++) {
            readWriteService.readByKey(PoolConstants.DEFAULT_CACHE_KEY_PREFIX + keySalt+ i);
        }
        watch.stop();
        log.info(watch.shortSummary());
    }

    private void executeWriteOperation(String keySalt) {
        log.info("Implement write operation {} times.", writeOperationCount);
        StopWatch watch = new StopWatch("Write");
        watch.start();
        for(int i = 0; i < writeOperationCount; i++) {
            readWriteService.writeByKey(PoolConstants.DEFAULT_CACHE_KEY_PREFIX + keySalt+ i, "BenchMark-"+ UUID.randomUUID());
        }
        watch.stop();
        log.info(watch.shortSummary());
    }


    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Redis-");
        executor.setCorePoolSize(25);
        return executor;
    }


}
