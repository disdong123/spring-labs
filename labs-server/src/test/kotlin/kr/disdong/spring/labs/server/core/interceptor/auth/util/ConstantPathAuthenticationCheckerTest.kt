package kr.disdong.spring.labs.server.core.interceptor.auth.util

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.mock.web.MockHttpServletRequest

internal class ConstantPathAuthenticationCheckerTest {

    private val sut = ConstantPathAuthenticationChecker()

    @ParameterizedTest
    @ValueSource(
        strings = [
            "/users/me",
            "/v1/auth/naver/login",
            "/v1/auth/kakao/logout/callback",
        ]
    )
    fun `요청 path 가 Constants 에 정의되어 있지 않으면 통과하지 않는다`(path: String) {
        val request = MockHttpServletRequest()
        request.servletPath = path

        val response = sut.check(request)

        assertFalse(response)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "/v1/auth/kakao/login",
            "/v1/auth/kakao/login/hello",
            "/v1/auth/kakao/login?hello=123",
        ]
    )
    fun `요청 path 가 Constants 에 정의되어 있으면 통과한다`(path: String) {
        val request = MockHttpServletRequest()
        request.servletPath = path

        val response = sut.check(request)

        assertTrue(response)
    }
}
