package kr.disdong.spring.labs.server.core.interceptor.auth

import kr.disdong.spring.labs.auth.common.exception.InvalidAccessTokenException
import kr.disdong.spring.labs.auth.module.kakao.AccessTokenVerifier
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.common.AbstractSpringBootTest
import kr.disdong.spring.labs.server.fixture.user.UserFixture
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

internal class AuthKakaoInterceptorTest : AbstractSpringBootTest() {

    @Autowired
    private lateinit var mapping: RequestMappingHandlerMapping

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var accessTokenVerifier: AccessTokenVerifier

    @BeforeEach
    fun setup() {
        whenever(accessTokenVerifier.verifyWithRefresh(any())).thenReturn(AccessTokenClaims(1))
        whenever(userRepository.findByPhone(any())).thenReturn(UserFixture.any())
    }

    @Nested
    @DisplayName("AuthGuard 어노테이션이 없거나 인증을 요구하지 않는 path 인 경우")
    inner class PassCase {
        @Test
        fun `true 를 반환한다`() {
            // given
            val servletRequest = MockHttpServletRequest("GET", "/v1/auth/kakao/login")
            val servletResponse = MockHttpServletResponse()
            val chain = mapping.getHandler(servletRequest)
            val sut = chain?.interceptorList
                ?.filter { AuthKakaoInterceptor::class.java.isInstance(it) }
                ?.get(0)

            // when
            val response = sut!!.preHandle(servletRequest, servletResponse, chain.handler)

            // then
            assertTrue(response)
        }
    }

    @Nested
    @DisplayName("AuthGuard 어노테이션이 있거나 인증을 요구하는 path 인 경우")
    inner class NoPassCase {
        @Test
        fun `Authorization 헤더가 없으면 예외가 발생한다`() {
            // given
            val servletRequest = MockHttpServletRequest("GET", "/users/me")
            val servletResponse = MockHttpServletResponse()
            val chain = mapping.getHandler(servletRequest)
            val sut = chain?.interceptorList
                ?.filter { AuthKakaoInterceptor::class.java.isInstance(it) }
                ?.get(0)

            // when, then
            assertThrows<InvalidAccessTokenException> {
                sut!!.preHandle(servletRequest, servletResponse, chain.handler)
            }
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "Bearer",
                "010",
                "Bear 010"
            ]
        )
        fun `Authorization 헤더의 값이 'Bearer token' 형식이 아니면 예외가 발생한다`(value: String) {
            // given
            val servletRequest = MockHttpServletRequest("GET", "/users/me")
            servletRequest.addHeader("Authorization", value)
            val servletResponse = MockHttpServletResponse()
            val chain = mapping.getHandler(servletRequest)
            val sut = chain?.interceptorList
                ?.filter { AuthKakaoInterceptor::class.java.isInstance(it) }
                ?.get(0)

            // when, then
            assertThrows<InvalidAccessTokenException> {
                sut!!.preHandle(servletRequest, servletResponse, chain.handler)
            }
        }

        @Test
        fun `Authorization 헤더의 값이 정상이면 true 를 반환한다`() {
            // given
            val servletRequest = MockHttpServletRequest("GET", "/users/me")
            servletRequest.addHeader("Authorization", "Bearer 010")
            val servletResponse = MockHttpServletResponse()
            val chain = mapping.getHandler(servletRequest)
            val sut = chain?.interceptorList
                ?.filter { AuthKakaoInterceptor::class.java.isInstance(it) }
                ?.get(0)

            // when
            val response = sut!!.preHandle(servletRequest, servletResponse, chain.handler)

            // then
            assertTrue(response)
        }
    }
}
