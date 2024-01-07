package kr.disdong.spring.labs.auth.module.kakao

import com.fasterxml.jackson.core.type.TypeReference
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import kr.disdong.spring.labs.auth.common.exception.InvalidAccessTokenException
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.common.time.Millis
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.common.token.TokenManager
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AccessTokenVerifier(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
    private val kakaoClient: KakaoClient,
) {

    /**
     * 토큰이 유효기간이 만료되었으면 refresh token 을 이용하여 access token 을 다시 받아옵니다.
     * 서비스의 access token 과 카카오의 access token 의 유효기간을 동일하게 가져갑니다.
     * @param token
     */
    @Transactional
    fun verifyWithRefresh(token: Token): AccessTokenClaims {
        try {
            return tokenManager.getCustomClaims(
                token,
                object : TypeReference<AccessTokenClaims>() {}
            )
        } catch (e: JwtException) {
            if (e is ExpiredJwtException) {
                val user = userRepository.findByAccessToken(token) ?: throw InvalidAccessTokenException(token)
                val response = kakaoClient.refreshAccessToken(user.userOauth.refreshToken!!)

                user.setAccessToken(tokenManager.create("user", AccessTokenClaims(user.id), Millis.HOUR))
                if (response.refreshToken != null) {
                    user.setRefreshToken(response.refreshToken)
                }

                return AccessTokenClaims(user.id)
            }

            throw e
        }
    }
}
