package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.naccl.model.vo.BlogInfo;
import top.naccl.model.vo.PageResult;
import top.naccl.service.RedisService;
import top.naccl.util.JacksonUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 读写Redis相关操作
 * @Author: Naccl
 * @Date: 2020-09-27
 */
@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	RedisTemplate jsonRedisTemplate;

	@Override
//	通过哈希获取博客信息页面结果
	public PageResult<BlogInfo> getBlogInfoPageResultByHash(String hash, Integer pageNum) {
		if (jsonRedisTemplate.opsForHash().hasKey(hash, pageNum)) {
			Object redisResult = jsonRedisTemplate.opsForHash().get(hash, pageNum);
			PageResult<BlogInfo> pageResult = JacksonUtils.convertValue(redisResult, PageResult.class);
			return pageResult;
		} else {
			return null;
		}
	}

	@Override
//	将 KV 保存到哈希
	public void saveKVToHash(String hash, Object key, Object value) {
		jsonRedisTemplate.opsForHash().put(hash, key, value);
	}

	@Override
//	保存映射到哈希
	public void saveMapToHash(String hash, Map map) {
		jsonRedisTemplate.opsForHash().putAll(hash, map);
	}

	@Override
//	按哈希获取地图
	public Map getMapByHash(String hash) {
		return jsonRedisTemplate.opsForHash().entries(hash);
	}

	@Override
//	通过哈希键获取值
	public Object getValueByHashKey(String hash, Object key) {
		return jsonRedisTemplate.opsForHash().get(hash, key);
	}

	@Override
//	按哈希键递增
	public void incrementByHashKey(String hash, Object key, int increment) {
		if (increment < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		jsonRedisTemplate.opsForHash().increment(hash, key, increment);
	}

	@Override
//	按哈希键删除
	public void deleteByHashKey(String hash, Object key) {
		jsonRedisTemplate.opsForHash().delete(hash, key);
	}

	@Override
	public <T> List<T> getListByValue(String key) {
		List<T> redisResult = (List<T>) jsonRedisTemplate.opsForValue().get(key);
		return redisResult;
	}

	@Override
//	将列表保存到值
	public <T> void saveListToValue(String key, List<T> list) {
		jsonRedisTemplate.opsForValue().set(key, list);
	}

	@Override
//	按值获取映射
	public <T> Map<String, T> getMapByValue(String key) {
		Map<String, T> redisResult = (Map<String, T>) jsonRedisTemplate.opsForValue().get(key);
		return redisResult;
	}

	@Override
//	保存映射到值
	public <T> void saveMapToValue(String key, Map<String, T> map) {
		jsonRedisTemplate.opsForValue().set(key, map);
	}

	@Override
//	按值获取对象
	public <T> T getObjectByValue(String key, Class t) {
		Object redisResult = jsonRedisTemplate.opsForValue().get(key);
		T object = (T) JacksonUtils.convertValue(redisResult, t);
		return object;
	}

	@Override
//	按键递增
	public void incrementByKey(String key, int increment) {
		if (increment < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		jsonRedisTemplate.opsForValue().increment(key, increment);
	}

	@Override
//	将对象保存到值
	public void saveObjectToValue(String key, Object object) {
		jsonRedisTemplate.opsForValue().set(key, object);
	}

	@Override
//	保存要设置的值
	public void saveValueToSet(String key, Object value) {
		jsonRedisTemplate.opsForSet().add(key, value);
	}

	@Override
//	按集合计数
	public int countBySet(String key) {
		return jsonRedisTemplate.opsForSet().size(key).intValue();
	}

	@Override
//	按集删除值
	public void deleteValueBySet(String key, Object value) {
		jsonRedisTemplate.opsForSet().remove(key, value);
	}

	@Override
//	集合里是否有值
	public boolean hasValueInSet(String key, Object value) {
		return jsonRedisTemplate.opsForSet().isMember(key, value);
	}

	@Override
//	按键删除缓存
	public void deleteCacheByKey(String key) {
		jsonRedisTemplate.delete(key);
	}

	@Override
//	是否有key
	public boolean hasKey(String key) {
		return jsonRedisTemplate.hasKey(key);
	}

	@Override
//	到期
	public void expire(String key, long time) {
		jsonRedisTemplate.expire(key, time, TimeUnit.SECONDS);
	}
}
