package kr.disdong.spring.labs.auth.module.kakao

import kr.disdong.spring.labs.auth.common.exception.AuthorizationCodeAccessDeniedException
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.auth.module.kakao.dto.LoginResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.OAuthCallbackResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.TokenResponse
import kr.disdong.spring.labs.auth.module.kakao.exception.UserNotFoundException
import kr.disdong.spring.labs.auth.module.kakao.extension.toLoginResponse
import kr.disdong.spring.labs.common.logger.logger
import kr.disdong.spring.labs.common.time.Millis
import kr.disdong.spring.labs.common.token.TokenManager
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserImpl
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KakaoService(
    private val kakaoClient: KakaoClient,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
) {

    private val logger = logger<KakaoService>()

    fun getLoginUrl(): String {
        return kakaoClient.getLoginUrl()
    }

    fun getLogoutUrl(claims: AccessTokenClaims): String {
        return kakaoClient.getLogoutUrl(claims)
    }

    /**
     * 카카오로 부터 리다이렉트되면 유저가 가지고 있는 토큰을 모두 삭제합니다.
     * @param id
     */
    @Transactional
    fun logout(id: Long) {
        val user = userRepository.findByUserId(id) ?: throw UserNotFoundException(id)
        user.removeTokens()
    }

    /**
     *
     * @param oAuthCallbackResponse
     */
    @Transactional
    fun login(oAuthCallbackResponse: OAuthCallbackResponse): LoginResponse {
        val response = getToken(oAuthCallbackResponse)
        val idToken = response.decodeIdToken()

        logger.info("idToken: $idToken")

        var user = userRepository.findByOauthIdAndType(idToken.sub, OauthType.KAKAO)
        if (user == null) {
            val userOauthMetadata = PlainUserOauthImpl(
                id = idToken.sub,
                nickname = idToken.nickname,
                type = OauthType.KAKAO,
            )

            user = userRepository.save(PlainUserImpl(plainUserOauth = userOauthMetadata))
        }

        logger.info("user: $user")

        // 카카오에서 받은 access token 은 사용하지 않습니다.
        val accessToken = tokenManager.create("user", AccessTokenClaims(user.id), Millis.HOUR)
        val refreshToken = response.refreshToken

        logger.info("token: ${accessToken.value} ${refreshToken.value}")

        user.setTokens(accessToken, refreshToken)

        return user.toLoginResponse(accessToken)
    }

    /**
     * access token 을 가져옵니다.
     * @param response
     */
    private fun getToken(response: OAuthCallbackResponse): TokenResponse {
        logger.info("getToken: $response")

        if (response.isAccessDenied()) {
            logger.error("response.error_description: ${response.error_description}")
            throw AuthorizationCodeAccessDeniedException()
        }

        return kakaoClient.getTokenWithAuthCode(response)
    }
}
