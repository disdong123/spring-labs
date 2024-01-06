package kr.disdong.spring.labs.server.module.user.service

import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.module.user.exception.UserNotFound
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun getByUserId(userId: Long) =
        userRepository.findByUserId(userId)
            ?: throw UserNotFound(userId)
}
