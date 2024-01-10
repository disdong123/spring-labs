package kr.disdong.spring.labs.auth.module.kakao.exception

import kr.disdong.spring.labs.auth.common.exception.AuthException
import java.util.UUID

class UserNotFoundException(private val userId: Long) : AuthException("$userId 유저를 찾을 수 없습니다.") {
    override fun getCode(): Int {
        return 404
    }
}

class SignupIdNotFoundException(private val signupId: UUID) : AuthException("$signupId 유저를 찾을 수 없습니다.") {
    override fun getCode(): Int {
        return 404
    }
}
