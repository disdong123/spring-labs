package kr.disdong.spring.labs.auth.core.listener.signup

import kr.disdong.spring.labs.auth.core.listener.signup.dto.PreSignupEvent
import kr.disdong.spring.labs.cache.module.user.PlainUserOauthCacheRepository
import kr.disdong.spring.labs.common.logger.logger
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PreSignupCacheListener(
    private val plainUserOauthCacheRepository: PlainUserOauthCacheRepository
) {

    private val logger = logger<PreSignupCacheListener>()

    @EventListener
    @Async
    @Transactional
    fun handle(event: PreSignupEvent) {
        logger.info("event: $event")
        plainUserOauthCacheRepository.save(event.key, event.userOauth)
    }
}
