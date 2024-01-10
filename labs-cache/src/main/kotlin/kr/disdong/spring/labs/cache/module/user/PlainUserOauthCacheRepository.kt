package kr.disdong.spring.labs.cache.module.user

import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.UUID

interface PlainUserOauthCacheRepository {
    fun save(key: UUID, user: PlainUserOauthImpl)
    fun findByKey(key: UUID): PlainUserOauthImpl?
}

@Component
class PlainUserOauthRedisRepository(
    private val redisTemplate: RedisTemplate<String, PlainUserOauthImpl>
) : PlainUserOauthCacheRepository {
    companion object {
        private const val PREFIX = "user"
        private val TTL = Duration.ofMinutes(10)
    }

    override fun save(key: UUID, user: PlainUserOauthImpl) {
        redisTemplate.opsForValue().set("$PREFIX:$key", user, TTL)
    }

    override fun findByKey(key: UUID): PlainUserOauthImpl? {
        return redisTemplate.opsForValue().get("$PREFIX:$key")
    }
}
