package com.jx.example.config.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.jx.example.controller.LoginController;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月8日 下午3:03:32
 *
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {
	
	private Logger logger = LogManager.getLogger(LoginController.class);
	
	@Bean  
    @ConditionalOnMissingBean(name = "redisTemplate")  
    public RedisTemplate<Object, Object> redisTemplate(  
            RedisConnectionFactory redisConnectionFactory) {  
        RedisTemplate<Object, Object> template = new RedisTemplate<>();  
  
        //使用fastjson序列化 ,没包 
        //FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        // value值的序列化采用fastJsonRedisSerializer  
        template.setValueSerializer(jackson2JsonRedisSerializer);  
        template.setHashValueSerializer(jackson2JsonRedisSerializer);  
        // key的序列化采用StringRedisSerializer  
        template.setKeySerializer(new StringRedisSerializer());  
        template.setHashKeySerializer(new StringRedisSerializer());  
  
        template.setConnectionFactory(redisConnectionFactory);  
        return template;  
    }  
  
    @Bean  
    @ConditionalOnMissingBean(StringRedisTemplate.class)  
    public StringRedisTemplate stringRedisTemplate(  
            RedisConnectionFactory redisConnectionFactory) {  
        StringRedisTemplate template = new StringRedisTemplate();  
        template.setConnectionFactory(redisConnectionFactory);  
        return template;  
    } 
}
