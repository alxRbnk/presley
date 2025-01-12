package org.rbnk.core.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import org.rbnk.core.cache.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Getter
public class CaffeineCacheService<T> implements CacheService<T> {

    private final Cache<Long, T> cache;

    public CaffeineCacheService(@Value("${cache.max-size}") int maxSize,
                                @Value("${cache.algorithm}") String algorithm) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(10, TimeUnit.MINUTES);
        if ("LRU".equalsIgnoreCase(algorithm)) {
        } else if ("LFU".equalsIgnoreCase(algorithm)) {
            caffeine = caffeine.recordStats();
        }
        this.cache = caffeine.build();
    }

    @Override
    public T get(Long id) {
        return cache.getIfPresent(id);
    }

    @Override
    public void put(Long id, T entity) {
        cache.put(id, entity);
    }

    @Override
    public void remove(Long id) {
        cache.invalidate(id);
    }

    @Override
    public void update(Long id, T entity) {
        cache.put(id, entity);
    }

    @Override
    public int size(){
        return cache.asMap().size();
    }
}
