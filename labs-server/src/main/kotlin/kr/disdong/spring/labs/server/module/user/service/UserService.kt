package kr.disdong.spring.labs.server.module.user.service

import kr.disdong.spring.labs.auth.core.listener.signup.dto.SignupEvent
import kr.disdong.spring.labs.auth.module.kakao.exception.SignupIdNotFoundException
import kr.disdong.spring.labs.auth.module.kakao.exception.UserNotFoundException
import kr.disdong.spring.labs.cache.module.user.PlainUserOauthCacheRepository
import kr.disdong.spring.labs.domain.module.user.repository.QUserRepository
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.module.user.dto.SignupBody
import kr.disdong.spring.labs.server.module.user.dto.UserResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val plainUserOauthCacheRepository: PlainUserOauthCacheRepository,
    private val qUserRepository: QUserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun getByUserId(userId: Long) =
        UserResponse.of(
            qUserRepository.findByUserId(userId)
                ?: throw UserNotFoundException(userId)
        )

    @Transactional
    fun signup(signupId: UUID, request: SignupBody) {
        val plainUserOauth = plainUserOauthCacheRepository.findByKey(signupId)
            ?: throw SignupIdNotFoundException(signupId)

        applicationEventPublisher.publishEvent(
            SignupEvent.of(userRepository.save(request.toPlainUser(plainUserOauth)))
        )
    }
}
