package com.batra.spring.reactive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {

	/**
	 * Returns a Spring bean with an instance of a <code>RedisConnectionFactory</code>.
	 * @return an instance of <code>LettuceConnectionFactory</code>.
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	/**
	 * Creates an instance of <code>RedisTemplate</code> to operate with Redis
	 * values containing Java objects.
	 * @param connectionFactory the <code>RedisConnectionFactory</code> to use
	 *                          for initializing the <code>RedisTemplate</code>.
	 * @return an instance of <code>RedisTemplate</code> that can be used to operate
	 * with Redis values containing Java objects.
	 */
	@Bean
	@Autowired
	public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
}