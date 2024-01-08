package kr.disdong.spring.labs.auth.module.kakao

import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.auth.module.kakao.dto.LogoutResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.OAuthCallbackResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.RefreshAccessTokenResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.TokenResponse
import kr.disdong.spring.labs.common.token.Token
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class KakaoClient(
    private val kakaoOauthBaseUrlProperties: KakaoOauthBaseUrlProperties,
    private val kakaoOauthProperties: KakaoOauthProperties,
) {

    // // TODO.
    // //  https://developers.kakao.com/docs/latest/ko/kakaologin/trouble-shooting
    // //  예외처리 필요.
    // private val kauthUrl = "https://kauth.kakao.com/oauth"
    // private val kapiUrl = "https://kapi.kakao.com/v1"

    private val restTemplate = RestTemplate()

    fun getLoginUrl(): String {
        return "${kakaoOauthBaseUrlProperties.kauth}/authorize?client_id=${kakaoOauthProperties.clientId}&redirect_uri=${kakaoOauthProperties.loginRedirectUri}&response_type=code&scope=openid"
    }

    fun getLogoutUrl(claims: AccessTokenClaims): String {
        return "${kakaoOauthBaseUrlProperties.kauth}/logout?client_id=${kakaoOauthProperties.clientId}&logout_redirect_uri=${kakaoOauthProperties.logoutRedirectUri}&state=${claims.id}"
    }

    /**
     * code 를 이용하여 access token 을 가져옵니다.
     * @param response
     */
    fun getTokenWithAuthCode(response: OAuthCallbackResponse): TokenResponse {
        val request = makeGetTokenRequest(response)

        return restTemplate
            .exchange("${kakaoOauthBaseUrlProperties.kauth}/token", HttpMethod.POST, request, TokenResponse::class.java)
            .body!!
    }

    /**
     * 로그아웃합니다.
     * 사용자 액세스 토큰과 리프레시 토큰을 모두 만료시킵니다.
     * @param accessToken
     */
    fun logout(accessToken: Token): ResponseEntity<LogoutResponse> {
        val request = makeLogoutRequest(accessToken)
        return restTemplate.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, request, LogoutResponse::class.java)
    }

    /**
     *
     * @param refreshToken
     */
    fun refreshAccessToken(refreshToken: Token): RefreshAccessTokenResponse {
        val request = makeRefreshAccessTokenRequest(refreshToken)

        return restTemplate
            .exchange("${kakaoOauthBaseUrlProperties.kauth}/token", HttpMethod.POST, request, RefreshAccessTokenResponse::class.java)
            .body!!
    }

    private fun makeGetTokenRequest(response: OAuthCallbackResponse): HttpEntity<MultiValueMap<String, String>> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "authorization_code")
        body.add("client_id", kakaoOauthProperties.clientId)
        body.add("redirect_uri", response.redirectUri ?: kakaoOauthProperties.loginRedirectUri) // client 에서 받아옵니다.
        body.add("code", response.code?.value)

        return HttpEntity(body, header)
    }

    private fun makeLogoutRequest(accessToken: Token): HttpEntity<Object> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        header.add("Authorization", "Bearer ${accessToken.value}")

        return HttpEntity<Object>(header)
    }

    private fun makeRefreshAccessTokenRequest(refreshToken: Token): HttpEntity<MultiValueMap<String, String>> {
        val header = HttpHeaders()

        header.add("Content-type", "application/x-www-form-urlencoded")

        // TODO dto 를 사용할 수 있는지 확인 필요.
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "refresh_token")
        body.add("client_id", kakaoOauthProperties.clientId)
        body.add("refresh_token", refreshToken.value) // client 에서 받아옵니다.

        return HttpEntity(body, header)
    }
}
