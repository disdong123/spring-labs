package kr.disdong.spring.labs.server.core.advice

import kr.disdong.spring.labs.common.dto.LabsResponse
import kr.disdong.spring.labs.common.exception.LabsException
import kr.disdong.spring.labs.common.logger.logger
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    private val logger = logger<ExceptionAdvice>()

    /**
     *
     */
    @ExceptionHandler(LabsException::class)
    @ResponseBody
    fun knittingException(e: LabsException): LabsResponse<LabsException> {
        val result = LabsResponse.of<LabsException>(e)
        logger.info("e: ${e.printStackTrace()}")
        return result
    }

    /**
     *
     */
    @ExceptionHandler(RuntimeException::class)
    @ResponseBody
    fun runtimeException(e: RuntimeException): ResponseEntity<Map<String, String>> {
        logger.error("e: ${e.printStackTrace()}")

        val responseHeaders = HttpHeaders()
        val httpStatus = HttpStatus.BAD_REQUEST
        val map = mutableMapOf<String, String>()

        map["reason_phrase"] = httpStatus.reasonPhrase
        map["code"] = "500"
        map["message"] = "알 수 없는 에러가 발생했습니다."

        return ResponseEntity(map, responseHeaders, httpStatus)
    }
}
