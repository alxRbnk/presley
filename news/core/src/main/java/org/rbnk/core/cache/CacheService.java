package org.rbnk.core.cache;

public interface CacheService<T> {
    T get(Long id);

    void put(Long id, T entity);

    void remove(Long id);

    void update(Long id, T entity);

    int size();
}
