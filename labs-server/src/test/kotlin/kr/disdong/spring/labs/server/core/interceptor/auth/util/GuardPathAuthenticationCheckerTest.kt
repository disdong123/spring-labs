package kr.disdong.spring.labs.server.core.interceptor.auth.util

import kr.disdong.spring.labs.auth.core.annotation.AuthGuard
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.web.method.HandlerMethod

internal class GuardPathAuthenticationCheckerTest {

    inner class TestController {
        @AuthGuard
        fun withGuard() {}

        fun withoutGuard() {}
    }

    private val sut = GuardPathAuthenticationChecker()

    @Test
    fun `메서드에 @AuthGuard 가 있으면 통과하지 않는다`() {
        assertFalse(sut.check(HandlerMethod(TestController(), TestController::class.java.getMethod("withGuard"))))
    }

    @Test
    fun `메서드에 @AuthGuard 가 없으면 통과한다`() {
        assertTrue(sut.check(HandlerMethod(TestController(), TestController::class.java.getMethod("withoutGuard"))))
    }
}
