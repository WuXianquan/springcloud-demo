package com.study.demo.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Lon
 * @Date: 2020/4/21 12:04
 * @Description: redis工具类
 */
@Component
public class RedisUtil<K, V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public String get(K key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (单位毫秒)
     *
     * @param key
     * @param value
     * @param time
     */
    public void setForTimeMS(K key, V value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (单位分钟)
     *
     * @param key
     * @param value
     * @param time
     */
    public void setForTimeM(K key, V value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param key
     * @param time
     * @param type
     * @return
     */
    public boolean expire(K key, long time, TimeUnit type) {
        return redisTemplate.boundValueOps(key).expire(time, type);
    }

    public String getHash(K key, Object hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 添加 Hash 键值对
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void putHash(K key, Object hashKey, V value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key
     * @param hashKeys
     * @return 删除成功的 数量
     */
    public Long deleteHash(K key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }
}