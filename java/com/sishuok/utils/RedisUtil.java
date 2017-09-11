package com.sishuok.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redicache 工具类
 */
@Slf4j
@SuppressWarnings("unchecked")
@Component
public class RedisUtil {
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

//	private static org.slf4j.Logger log = LoggerFactory.getLogger(RedisUtil.class);

	public Boolean expireAt(Object key, final Date date) {
		return redisTemplate.expireAt(key, date);
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @return
	 */
	public boolean hset(Object key, Map m) {
		boolean result = false;
		try {
			redisTemplate.opsForHash().putAll(key, m);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("### " + e);
		}
		return result;
	}

	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	public String remove(final String key) {
		String value = null;
		try {
			if (exists(key)) {
				value = get(key);
				redisTemplate.delete(key);
			}
		} catch (Exception e) {
			log.error("RedisUtil remove method error!!!", e);
		}

		return value;
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return stringRedisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		String result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = String.valueOf(operations.get(key));
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取列表表头的值
	 *
	 * @param key
	 * @return
	 */
	public String lpop(final String key) {
		String result = null;
		try {
			if (exists(key)) {
				result = stringRedisTemplate.opsForList().leftPop(key);
			}
		} catch (Exception e) {
			log.error("RedisUtil lpop method error!!!", e);
		}
		return result;
	}

	/**
	 * 添加列表表头的值
	 *
	 * @param key
	 * @return
	 */
	public Long rpush(final String key, final String value) {
		long result = 0L;
		try {
			result = stringRedisTemplate.opsForList().rightPush(key, value);
		} catch (Exception e) {
			log.error("RedisUtil rpush method error!!!", e);
		}
		return result;
	}
}