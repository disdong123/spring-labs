package kr.disdong.spring.labs.auth.module.kakao

import kr.disdong.spring.labs.auth.common.AbstractSpringBootTest
import kr.disdong.spring.labs.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.spring.labs.auth.module.kakao.dto.OAuthCallbackResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.TokenResponse
import kr.disdong.spring.labs.common.token.Token
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
import org.mockito.kotlin.times
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

            userRepository.save(PlainUserImpl(plainUserOauth = PlainUserOauthImpl("", "", OauthType.KAKAO)))
        }

        @Test
        fun `유저가 없는 경우 유저 정보를 생성하고 로그인을 시킨다`() {
            val response = sut.login(OAuthCallbackResponse(code = Token("")))

            assertNotNull(response.accessToken)
            assertNull(response.name)
            assertNull(response.phone)
            assertEquals(response.oauthType, OauthType.KAKAO)
            assertNotNull(userRepository.findByUserId(response.id))
        }

        @Test
        fun `이미 유저가 있으면 토큰을 발급하고 로그인을 시킨다`() {
            userRepository.save(PlainUserImpl(plainUserOauth = PlainUserOauthImpl("1", type = OauthType.KAKAO, nickname = "cola")))

            val response = sut.login(OAuthCallbackResponse(code = Token("")))

            assertNotNull(response.accessToken)
            assertNull(response.name)
            assertNull(response.phone)
            assertEquals(response.oauthType, OauthType.KAKAO)
            assertNotNull(userRepository.findByUserId(response.id))
        }
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
