package kr.disdong.spring.labs.auth.module.kakao

import kr.disdong.spring.labs.auth.common.AbstractSpringBootTest
import kr.disdong.spring.labs.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.spring.labs.auth.module.kakao.dto.OAuthCallbackResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.TokenResponse
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.Address
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserImpl
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired

internal class KakaoServiceITest : AbstractSpringBootTest() {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var sut: KakaoService

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
    }

    @Nested
    @DisplayName("사용자가 로그인을 완료하면 인가 코드와 함께 지정된 url 로 리다이렉트된다.")
    inner class LoginTest {
        @BeforeEach
        fun setup() {
            whenever(kakaoClient.getTokenWithAuthCode(any())).thenReturn(`인가 코드로 조회한 결과 값`())
        }

        @Test
        fun `error 가 access_denied 면 예외가 발생한다`() {
            assertThrows(AuthorizationCodeAccessDeniedException::class.java) {
                sut.login(OAuthCallbackResponse(code = Token(""), error = "access_denied"))
            }

            verify(kakaoClient, never()).getTokenWithAuthCode(any())
        }

        @DisplayName("유저가 없는 경우")
        @Nested
        inner class PreSignupCase {
            @Test
            fun `회원가입을 위해 oauth 정보를 캐싱하고 키를 반환한다`() {
                // when
                val response = sut.login(OAuthCallbackResponse(code = Token("")))

                // then
                verify(kakaoClient, times(1)).getTokenWithAuthCode(any())
                assertNotNull(response.signupPhase)
                assertNull(response.loginPhase)
                verify(plainUserOauthCacheRepository, times(1)).save(any(), any())
            }
        }

        @DisplayName("유저가 있는 경우")
        @Nested
        inner class LoginCase {
            @Test
            fun `토큰을 발급하고 로그인을 시킨다`() {
                // given
                `유저 정보 저장`()

                // when
                val response = sut.login(OAuthCallbackResponse(code = Token("")))

                // then
                verify(kakaoClient, times(1)).getTokenWithAuthCode(any())
                assertNull(response.signupPhase)
                assertNotNull(response.loginPhase!!.accessToken)
                assertNotNull(response.loginPhase!!.name)
                assertNotNull(response.loginPhase!!.phone)
                assertEquals(response.loginPhase!!.oauthType, OauthType.KAKAO)
            }
        }
    }

    private fun `유저 정보 저장`() {
        userRepository.save(
            PlainUserImpl(
                plainUserOauth = PlainUserOauthImpl("117847912875913905493", type = OauthType.KAKAO, nickname = "cola"),
                name = "aa",
                phone = "01012345678",
                address = Address("서울시 강남구", "123-456"),
            )
        )
    }

    private fun `인가 코드로 조회한 결과 값`(): TokenResponse {
        return TokenResponse(
            tokenType = "",
            accessToken = Token(""),
            idToken = Token("eyJhbGciOiJSUzI1NiIsImtpZCI6ImFmZmM2MjkwN2E0NDYxODJhZGMxZmE0ZTgxZmRiYTYzMTBkY2U2M2YifQ.eyJhenAiOiIyNzIxOTYwNjkxNzMtZm81ZWI0MXQzbmR1cTZ1ZXRkc2pkdWdzZXV0ZnBtc3QuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNzIxOTYwNjkxNzMtZm81ZWI0MXQzbmR1cTZ1ZXRkc2pkdWdzZXV0ZnBtc3QuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTc4NDc5MTI4NzU5MTM5MDU0OTMiLCJlbWFpbCI6ImFhcm9uLnBhcmVja2lAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJpRVljNDBUR0luUkhoVEJidWRncEpRIiwiZXhwIjoxNTI0NTk5MDU2LCJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJpYXQiOjE1MjQ1OTU0NTZ9.ho2czp_1JWsglJ9jN8gCgWfxDi2gY4X5-QcT56RUGkgh5BJaaWdlrRhhN_eNuJyN3HRPhvVA_KJVy1tMltTVd2OQ6VkxgBNfBsThG_zLPZriw7a1lANblarwxLZID4fXDYG-O8U-gw4xb-NIsOzx6xsxRBdfKKniavuEg56Sd3eKYyqrMA0DWnIagqLiKE6kpZkaGImIpLcIxJPF0-yeJTMt_p1NoJF7uguHHLYr6752hqppnBpMjFL2YMDVeg3jl1y5DeSKNPh6cZ8H2p4Xb2UIrJguGbQHVIJvtm_AspRjrmaTUQKrzXDRCfDROSUU-h7XKIWRrEd2-W9UkV5oCg"),
            expiresIn = 1,
            refreshToken = Token(""),
            refreshTokenExpiresIn = 1,
            scope = ""
        )
    }
}
