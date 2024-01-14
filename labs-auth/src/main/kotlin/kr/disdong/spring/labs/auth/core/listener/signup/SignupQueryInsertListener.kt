package kr.disdong.spring.labs.auth.core.listener.signup

import kr.disdong.spring.labs.auth.core.listener.signup.dto.SignupEvent
import kr.disdong.spring.labs.common.logger.logger
import kr.disdong.spring.labs.domain.module.user.repository.QUserRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SignupQueryInsertListener(
    private val qUserRepository: QUserRepository,
) {

    private val logger = logger<SignupQueryInsertListener>()

    @EventListener
    fun handle(event: SignupEvent) {
        logger.info("event: $event")
        qUserRepository.save(event.toQUser())
    }
}
