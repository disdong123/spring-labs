package kr.disdong.spring.labs.auth.core.listener.login

import kr.disdong.spring.labs.auth.core.listener.login.dto.LoginEvent
import kr.disdong.spring.labs.common.logger.logger
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class LoginTokenCachingListener() {

    private val logger = logger<LoginTokenCachingListener>()

    @EventListener
    @Async
    fun handle(event: LoginEvent) {
        logger.info("event: $event")
    }
}
