package cn.xm.exam.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class EhcacheUtils {
	public static final String CACHE_NAME1 = "cache1";
	public static final String CACHE_NAME2 = "cache2";

	public static final int CACHE_ONE_HOUR = 1 * 60 * 60;
	public static final int CACHE_ONE_DAY = 24 * 60 * 60;

	private static CacheManager cacheManager = CacheManager.create();
	// 静态代码块创建缓存
	static {
		cacheManager.addCache(CACHE_NAME1);
		cacheManager.addCache(CACHE_NAME2);

		initCacheSettings(cacheManager.getCache(CACHE_NAME1));
		initCacheSettings(cacheManager.getCache(CACHE_NAME2));
	}

	private static void initCacheSettings(Cache cache) {
		CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
		cacheConfiguration.setTimeToIdleSeconds(8 * 60 * 60);
		cacheConfiguration.setTimeToLiveSeconds(24 * 60 * 60);
		// cacheConfiguration.setMaxElementsInMemory(4);
		// cacheConfiguration.setMaxElementsOnDisk(4);
		// cacheConfiguration.setName("cache_test_update");
		cacheConfiguration.setMemoryStoreEvictionPolicy("CLOCK");
	}

	private EhcacheUtils() {

	}

	// 1.增加元素
	/**
	 * 向指定的缓存中增加元素
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 *            缓存的key
	 * @param value
	 *            缓存d值
	 * @param seconds
	 *            缓存的时间(秒)
	 * @param override
	 *            如果存在是否覆盖
	 * @return
	 */
	public static boolean addCache(String cacheName, String key, Object value, int seconds, boolean override) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			Object tmpValue = getValueByCacheKey(cacheName, key);
			if (tmpValue != null) {
				if (!override) {
					return true;
				}
			}
			cache.put(new Element(key, value, false, 0, seconds));
			cache.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 2.删除元素
	// 2.1删除单个元素
	/**
	 * 删除单个元素
	 * 
	 * @param cacheName
	 *            缓存的名称
	 * @param key
	 *            缓存的key
	 * @return
	 */
	public static boolean removeCacheByKey(String cacheName, String key) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			cache.remove(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 2.2删除全部元素
	/**
	 * 删除所有元素
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @return
	 */
	public static boolean removeAllByCacheName(String cacheName) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			cache.removeAll();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 3.获取元素
	// 3.1获取单个元素
	/**
	 * 获取单个元素
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 *            缓存的key
	 * @return
	 */
	public static Object getValueByCacheKey(String cacheName, String key) {
		try {
			Cache cache = cacheManager.getCache(cacheName);
			Element element = cache.get(key);
			return element == null ? null : element.getObjectValue();
		} catch (Exception e) {
			return null;
		}
	}

	// 3.1获取全部元素
	/**
	 * 获取所有缓存d元素
	 * 
	 * @param cacheName
	 *            缓存的名称
	 * @return
	 */
	public static List<Object> getAllValuesByCacheName(String cacheName) {
		List<Object> result = new ArrayList<Object>();
		try {
			Cache cache = cacheManager.getCache(cacheName);
			for (Object key : cache.getKeys()) {
				Element element = cache.get(key);
				result.add(element.getObjectValue());
			}
			return result;
		} catch (Exception e) {
			return result;
		}
	}

	// 4.获取配置
	public static Map<String, String> getConfigurations(String cacheName) {
		Map<String, String> results = new HashMap<String, String>();
		Cache cache = cacheManager.getCache(cacheName);
		CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();

		MemoryStoreEvictionPolicy policy = cacheConfiguration.getMemoryStoreEvictionPolicy();
		results.put("timeToIdleSeconds", String.valueOf(cacheConfiguration.getTimeToIdleSeconds()));
		results.put("timeToLiveSeconds", String.valueOf(cacheConfiguration.getTimeToLiveSeconds()));
		results.put("maxElementsInMemory", String.valueOf(cacheConfiguration.getMaxElementsInMemory()));
		results.put("policy", policy.toString());
		return results;
	}
}