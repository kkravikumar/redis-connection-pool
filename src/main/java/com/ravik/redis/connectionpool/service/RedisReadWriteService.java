package com.ravik.redis.connectionpool.service;

import com.ravik.redis.connectionpool.config.PoolConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisReadWriteService {
    private final Cache cache;

    @Autowired
    public RedisReadWriteService(CacheManager cacheManager){
        cache = cacheManager.getCache(PoolConstants.DEFAULT_CACHE_NAME);
    }

    public String readByKey(String key){
        final Cache.ValueWrapper valueWrapper = cache.get(key);
        return valueWrapper != null ? valueWrapper.get().toString() : "Cache value not found!";
    }

    public void writeByKey(String key, String value){
        cache.put(key,value);
    }
}
