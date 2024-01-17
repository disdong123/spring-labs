package kr.disdong.spring.labs.server.module.user.service

import kr.disdong.spring.labs.auth.module.kakao.exception.SignupIdNotFoundException
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl
import kr.disdong.spring.labs.domain.module.user.repository.QUserRepository
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.common.IntegrationTest
import kr.disdong.spring.labs.server.module.user.dto.SignupBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

internal class UserServiceITest : IntegrationTest() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var qUserRepository: QUserRepository

    @Autowired
    private lateinit var sut: UserService

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
        qUserRepository.deleteAll()
    }

    @Nested
    @DisplayName("pre-signup 단계 이후 유저 정보를 저장할 때")
    inner class SignupTest {
        @Test
        fun `signupId 가 캐싱되어있지 않으면 예외가 발생한다`() {
            // given
            val uuid = UUID.randomUUID()
            val request = SignupBody("name", "phone", "address", "addressDetail")

            // when, then
            assertThrows(SignupIdNotFoundException::class.java) {
                sut.signup(uuid, request)
            }
        }

        @Nested
        @DisplayName("signupId 가 캐싱되어있으면")
        inner class SuccessCase {
            @Test
            fun `캐싱된 정보와 함께 유저 정보를 저장한다`() {
                // given
                val uuid = UUID.randomUUID()
                val request = SignupBody("name", "phone", "address", "addressDetail")
                whenever(plainUserOauthCacheRepository.findByKey(any())).thenReturn(
                    PlainUserOauthImpl("kakao", "1234", OauthType.KAKAO)
                )

                // when
                sut.signup(uuid, request)

                // then
                assertEquals(userRepository.findAll().size, 1)
            }

            @Test
            fun `query 용 db 에 데이터를 저장한다`() {
                // given
                val uuid = UUID.randomUUID()
                val request = SignupBody("name", "phone", "address", "addressDetail")
                whenever(plainUserOauthCacheRepository.findByKey(any())).thenReturn(
                    PlainUserOauthImpl("kakao", "1234", OauthType.KAKAO)
                )

                // when
                sut.signup(uuid, request)

                // then
                val user = userRepository.findAll()[0]
                val qUser = qUserRepository.findByUserId(user.id)
                assertEquals(qUser?.name, user.name)
                assertEquals(qUser?.phone, user.phone)
                assertEquals(qUser?.address, user.address.address)
                assertEquals(qUser?.addressDetail, user.address.addressDetail)
            }
        }
    }
}
