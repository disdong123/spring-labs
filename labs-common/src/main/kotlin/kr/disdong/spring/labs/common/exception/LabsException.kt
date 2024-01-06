package kr.disdong.spring.labs.common.exception

import java.lang.RuntimeException

/**
 *
 * @property message
 */
abstract class LabsException(
    override val message: String,
) : RuntimeException(message) {

    abstract fun getCode(): Int
}
