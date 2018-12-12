package cn.xm.exam.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EhcacheTest {
	public static void main(String[] args) {
		List<String> cache1 = new ArrayList<>();
		cache1.add("111");
		cache1.add("222");

		EhcacheUtils.addCache(EhcacheUtils.CACHE_NAME1, "cache1", cache1, EhcacheUtils.CACHE_ONE_HOUR, false);

		String cache2 = "cache2";
		EhcacheUtils.addCache(EhcacheUtils.CACHE_NAME1, "cache2", cache2, EhcacheUtils.CACHE_ONE_HOUR, false);

		Map cache3 = new HashMap();
		cache3.put("1", "111222");
		cache3.put("2", "111222333");
		EhcacheUtils.addCache(EhcacheUtils.CACHE_NAME1, "cache3", cache3, EhcacheUtils.CACHE_ONE_HOUR, false);

		Map valueByCacheKey = (Map) EhcacheUtils.getValueByCacheKey(EhcacheUtils.CACHE_NAME1, "cache3");
		System.out.println(valueByCacheKey);

		EhcacheUtils.removeCacheByKey(EhcacheUtils.CACHE_NAME1, "cache2");
		List<Object> allValuesByCacheName = EhcacheUtils.getAllValuesByCacheName(EhcacheUtils.CACHE_NAME1);
		for (Object obj : allValuesByCacheName) {
			System.out.println(obj);
		}

		Map<String, String> configurations = EhcacheUtils.getConfigurations(EhcacheUtils.CACHE_NAME1);
		System.out.println(configurations);
	}
}
