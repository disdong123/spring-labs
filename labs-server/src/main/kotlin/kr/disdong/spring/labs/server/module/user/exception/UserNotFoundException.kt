package kr.disdong.spring.labs.server.module.user.exception

import kr.disdong.spring.labs.common.exception.LabsException

class UserNotFoundException(private val userId: Long) : LabsException("$userId 유저를 찾을 수 없습니다.") {
    override fun getCode(): Int {
        return 404
    }
}
