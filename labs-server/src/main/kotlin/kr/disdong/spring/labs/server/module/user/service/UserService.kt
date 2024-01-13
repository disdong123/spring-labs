package kr.disdong.spring.labs.server.module.user.service

import kr.disdong.spring.labs.auth.core.listener.signup.dto.SignupEvent
import kr.disdong.spring.labs.auth.module.kakao.exception.SignupIdNotFoundException
import kr.disdong.spring.labs.auth.module.kakao.exception.UserNotFoundException
import kr.disdong.spring.labs.cache.module.user.PlainUserOauthCacheRepository
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.module.user.dto.SignupBody
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val plainUserOauthCacheRepository: PlainUserOauthCacheRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun getByUserId(userId: Long) =
        userRepository.findByUserId(userId)
            ?: throw UserNotFoundException(userId)

    @Transactional
    fun signup(signupId: UUID, request: SignupBody) {
        val plainUserOauth = plainUserOauthCacheRepository.findByKey(signupId)
            ?: throw SignupIdNotFoundException(signupId)

        val user = userRepository.save(request.toPlainUser(plainUserOauth))

        applicationEventPublisher.publishEvent(
            SignupEvent(
                name = user.name,
                phone = user.phone,
            )
        )
    }
}
