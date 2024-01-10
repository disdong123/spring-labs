package kr.disdong.spring.labs.auth.common

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer

@TestConfiguration
class EmbeddedRedisConfig(redisProperties: RedisProperties) {
    private val redisServer: RedisServer

    init {
        redisServer = RedisServer(redisProperties.port)
    }

    @PostConstruct
    fun postConstruct() {
        redisServer.start()
    }

    @PreDestroy
    fun preDestroy() {
        redisServer.stop()
    }
}
