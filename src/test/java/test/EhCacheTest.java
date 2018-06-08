package test;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.junit.Test;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月8日 上午9:49:49
 *
 */
public class EhCacheTest {

	public static void main(String[] args) {
	}

	@Test
	public void getKeyValue() {
		EhCacheManager manager = new EhCacheManager();
		manager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        Cache<Object, Object> cache = manager.getCache("passwordRetryCache");  
		System.out.println(cache.get("222222@qq.com"));

	}
}
