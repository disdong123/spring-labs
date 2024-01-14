package kr.disdong.spring.labs.server.module.user.service

import kr.disdong.spring.labs.auth.module.kakao.exception.SignupIdNotFoundException
import kr.disdong.spring.labs.cache.module.user.PlainUserOauthCacheRepository
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl
import kr.disdong.spring.labs.domain.module.user.repository.QUserRepository
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.fixture.user.UserFixture
import kr.disdong.spring.labs.server.module.user.dto.SignupBody
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.context.ApplicationEventPublisher
import java.util.UUID

internal class UserServiceTest {
    private val userRepository = mock<UserRepository>()
    private val plainUserOauthCacheRepository = mock<PlainUserOauthCacheRepository>()
    private val qUserRepository = mock<QUserRepository>()
    private val applicationEventPublisher = mock<ApplicationEventPublisher>()
    private val sut = UserService(
        userRepository = userRepository,
        plainUserOauthCacheRepository = plainUserOauthCacheRepository,
        qUserRepository = qUserRepository,
        applicationEventPublisher = applicationEventPublisher,
    )

    @Nested
    @DisplayName("pre-signup 단계 이후 유저 정보를 저장할 때")
    inner class SignupTest {
        @Test
        fun `signupId 가 캐싱되어있지 않으면 예외가 발생한다`() {
            // given
            whenever(plainUserOauthCacheRepository.findByKey(any())).thenReturn(null)

            // when, then
            assertThrows(SignupIdNotFoundException::class.java) {
                sut.signup(UUID.randomUUID(), SignupBody("name", "phone", "address", "addressDetail"))
            }
        }

        @Test
        fun `signupId 가 캐싱되어있으면 캐싱된 정보와 함께 유저 정보를 저장한다`() {
            // given
            whenever(plainUserOauthCacheRepository.findByKey(any())).thenReturn(PlainUserOauthImpl("kakao", "1234", OauthType.KAKAO))
            whenever(userRepository.save(any())).thenReturn(UserFixture.any())

            // when, then
            sut.signup(UUID.randomUUID(), SignupBody("name", "phone", "address", "addressDetail"))
            verify(userRepository, times(1)).save(any())
        }
    }
}
