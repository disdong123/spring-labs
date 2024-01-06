package kr.disdong.spring.labs.auth.common.exception

import kr.disdong.spring.labs.common.exception.LabsException

/**
 *
 * @property message
 */
abstract class AuthException(
    override val message: String,
) : LabsException(message) {

    abstract override fun getCode(): Int
}
