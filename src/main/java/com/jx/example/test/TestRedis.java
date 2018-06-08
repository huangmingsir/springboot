package com.jx.example.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月8日 下午4:49:47
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestRedis {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void set() {
		redisTemplate.opsForValue().set("test:set", "testValue1");
	}
}
