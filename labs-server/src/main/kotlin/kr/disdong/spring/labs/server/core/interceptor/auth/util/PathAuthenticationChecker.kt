package kr.disdong.spring.labs.server.core.interceptor.auth.util

import jakarta.servlet.http.HttpServletRequest
import kr.disdong.spring.labs.auth.core.annotation.AuthGuard
import kr.disdong.spring.labs.common.logger.logger
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import java.lang.RuntimeException

interface PathAuthenticationChecker {

    fun check(request: Any): Boolean
}

@Component
class GuardPathAuthenticationChecker : PathAuthenticationChecker {
    private val logger = logger<GuardPathAuthenticationChecker>()

    override fun check(request: Any): Boolean {
        try {
            val auth = (request as HandlerMethod).getMethodAnnotation(AuthGuard::class.java)
            auth ?: return true
        } catch (err: RuntimeException) {
            logger.debug("AuthGuard 어노테이션이 없는 경우에는 true 를 반환합니다.")
            return true
        }

        return false
    }
}

@Component
class ConstantPathAuthenticationChecker : PathAuthenticationChecker {
    private val logger = logger<ConstantPathAuthenticationChecker>()

    override fun check(request: Any): Boolean {
        request as HttpServletRequest
        Constants.BYPASS_LIST.forEach {
            if (it.contains("**")) {
                if (request.servletPath.startsWith(it.substring(0..it.lastIndex - 3))) {
                    return true
                }

                return@forEach
            }

            if (request.servletPath.equals(it.substring(0..it.lastIndex))) {
                return true
            }

            return@forEach
        }

        return false
    }
}
