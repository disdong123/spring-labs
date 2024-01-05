package kr.disdong.springboot.template.server.module.user.service

import kr.disdong.springboot.template.domain.module.user.model.User
import kr.disdong.springboot.template.domain.module.user.repository.UserRepository
import kr.disdong.springboot.template.server.module.user.dto.CreateUserBody
import kr.disdong.springboot.template.server.module.user.exception.UserNotFound
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun getByUserId(userId: Long) =
        userRepository.findByUserId(userId)
            ?: throw UserNotFound(userId)

    @Transactional
    fun create(request: CreateUserBody): User {
        return userRepository.save(request.toUser())
    }
}
