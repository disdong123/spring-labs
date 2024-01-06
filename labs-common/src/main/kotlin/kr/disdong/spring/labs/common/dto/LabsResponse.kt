package kr.disdong.spring.labs.common.dto

import kr.disdong.spring.labs.common.exception.LabsException
import org.springframework.http.HttpStatus

class LabsResponse<T>(
    val code: Int,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> of(
            exception: LabsException
        ): LabsResponse<T> {
            return LabsResponse(
                code = exception.getCode(),
                message = exception.message,
            )
        }

        fun <T> of(
            content: T? = null,
        ): LabsResponse<T> {
            return LabsResponse(
                code = HttpStatus.OK.value(),
                data = content
            )
        }

        fun <T> of(
            code: HttpStatus,
            content: T? = null,
        ): LabsResponse<T> {
            return LabsResponse(
                code = code.value(),
                data = content
            )
        }
    }
}
