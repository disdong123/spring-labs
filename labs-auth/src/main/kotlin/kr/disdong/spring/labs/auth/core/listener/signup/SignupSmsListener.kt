package kr.disdong.spring.labs.auth.core.listener.signup

import kr.disdong.spring.labs.auth.core.listener.signup.dto.SignupEvent
import kr.disdong.spring.labs.common.logger.logger
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SignupSmsListener {

    private val logger = logger<SignupSmsListener>()

    @EventListener
    @Async
    @Transactional
    fun handle(event: SignupEvent) {
        logger.info("event: $event")
    }
}
