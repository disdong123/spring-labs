package kr.disdong.spring.labs.server.core.interceptor.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.disdong.spring.labs.auth.common.exception.InvalidAccessTokenException
import kr.disdong.spring.labs.auth.module.kakao.AccessTokenVerifier
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.common.logger.logger
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.server.core.interceptor.auth.util.PathAuthenticationChecker
import org.springframework.stereotype.Component
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthKakaoInterceptor(
    private val userRepository: UserRepository,
    private val accessTokenVerifier: AccessTokenVerifier,
    private val guardPathAuthenticationChecker: PathAuthenticationChecker,
) : HandlerInterceptor {

    private val logger = logger<AuthKakaoInterceptor>()

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (guardPathAuthenticationChecker.check(handler)) {
            return true
        }

        val authorization = request.getHeader("Authorization")
        val token = validateAuthorizationHeader(authorization)
        if (token.isPhone()) {
            val user = userRepository.findByPhone(token.value) ?: throw InvalidAccessTokenException(token)
            setCustomClaimsOnRequest(request, AccessTokenClaims(user.id))
            return true
        }

        setCustomClaimsOnRequest(request, accessTokenVerifier.verifyWithRefresh(token))
        return true
    }

    /**
     * TODO.
     *  setAttribute 로 custom claims 값을 저장합니다.
     * @param request
     * @param claims
     */
    private fun setCustomClaimsOnRequest(request: HttpServletRequest, claims: AccessTokenClaims) {
        ServletWebRequest(request).setAttribute("AccessTokenClaims", claims, 0)
    }

    /**
     * 정상적인 헤더값인지 확인한 후, 토큰을 반환합니다.
     * @param authorization
     * @return
     */
    private fun validateAuthorizationHeader(authorization: String?): Token {
        if (authorization == null) {
            logger.debug("토큰이 제공되지 않았습니다.")
            throw InvalidAccessTokenException()
        }

        val tokens = authorization.split(" ")
        if (tokens.size < 2) {
            logger.debug("토큰이 제공되지 않았습니다.")
            throw InvalidAccessTokenException()
        }

        if (tokens[0] != "Bearer" || tokens[1] == "") {
            logger.info("token 이 없습니다.")
            throw InvalidAccessTokenException()
        }

        return Token(tokens[1])
    }
}
