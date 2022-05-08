package com.batra.spring.reactive.config;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

	@Bean
	public RedisSerializer<Person> personJsonSerializer() {
		return new Jackson2JsonRedisSerializer<>(Person.class);
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
	public RedisTemplate<String, Person> redisTemplate(final RedisConnectionFactory connectionFactory, final RedisSerializer<Person> personRedisSerializer) {
		RedisTemplate<String, Person> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setValueSerializer(personRedisSerializer);
		redisTemplate.setHashValueSerializer(personRedisSerializer);
		return redisTemplate;
	}
}