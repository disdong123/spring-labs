package kr.disdong.spring.labs.server.module.auth.controller

import jakarta.servlet.http.HttpServletResponse
import kr.disdong.spring.labs.auth.core.annotation.AuthGuard
import kr.disdong.spring.labs.auth.core.annotation.CurrentUserClaims
import kr.disdong.spring.labs.auth.module.kakao.KakaoService
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.auth.module.kakao.dto.LoginResponse
import kr.disdong.spring.labs.auth.module.kakao.dto.OAuthCallbackResponse
import kr.disdong.spring.labs.common.dto.LabsResponse
import kr.disdong.spring.labs.common.logger.logger
import kr.disdong.spring.labs.server.module.auth.controller.spec.KakaoSpec
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth/kakao")
class KakaoController(
    private val kakaoService: KakaoService,
) : KakaoSpec {

    private val logger = logger<KakaoController>()

    @GetMapping("/signup")
    fun signup() {
    }

    /**
     * kakao 로그인 페이지로 리다이렉트합니다.
     * 서버 테스트용으로, 클라이언트에서 할 수도 있습니다.
     * @param httpServletResponse
     */
    @GetMapping("/login")
    override fun login(httpServletResponse: HttpServletResponse): LabsResponse<Unit> {
        logger.info("login()")
        httpServletResponse.sendRedirect(kakaoService.getLoginUrl())
        return LabsResponse.of()
    }

    /**
     * 유저가 카카오 로그인 완료 시, 리다이렉트되는 api 입니다.
     * @param response
     */
    @GetMapping("/login/callback")
    override fun loginCallback(response: OAuthCallbackResponse): LabsResponse<LoginResponse> {
        logger.info("login(response: $response) ${response.code}")
        return LabsResponse.of(kakaoService.login(response))
    }

    /**
     * 브라우저와 kakao 와의 세션을 완전히 끊을 수도 있습니다.
     * 서버 테스트용으로, 클라이언트에서 할 수도 있습니다.
     * @param httpServletResponse
     */
    @GetMapping("/logout")
    @AuthGuard
    override fun logout(
        httpServletResponse: HttpServletResponse,
        @CurrentUserClaims claims: AccessTokenClaims,
    ): LabsResponse<Unit> {
        logger.info("logout(claims: $claims)")
        httpServletResponse.sendRedirect(kakaoService.getLogoutUrl(claims))
        return LabsResponse.of()
    }

    /**
     *
     * @param state user id 값입니다. logout-with-kakao 에서 넘겨준 state 를 받습니다.
     * @return
     */
    @GetMapping("/logout/callback")
    override fun logoutCallback(state: Long): LabsResponse<Unit> {
        logger.info("logoutCallback(state: $state}")
        kakaoService.logout(state)
        return LabsResponse.of()
    }
}
