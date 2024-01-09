package kr.disdong.spring.labs.cache.core.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    @Primary
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(
        RedisStandaloneConfiguration().apply {
            hostName = redisProperties.host
            port = redisProperties.port
            username = redisProperties.username
            password = RedisPassword.of(redisProperties.password)
        },
        LettuceClientConfiguration.builder().useSsl().build()
    )

    @Bean
    fun redisTemplate(
        @Qualifier("redisConnectionFactory")
        redisConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> = RedisTemplate<String, String>().apply {
        setConnectionFactory(redisConnectionFactory)
        setEnableTransactionSupport(true)
        keySerializer = StringRedisSerializer()
        valueSerializer = StringRedisSerializer()
    }
}
