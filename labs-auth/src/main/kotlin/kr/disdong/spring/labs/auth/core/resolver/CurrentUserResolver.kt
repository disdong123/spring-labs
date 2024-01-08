package kr.disdong.spring.labs.auth.core.resolver

import kr.disdong.spring.labs.auth.core.annotation.CurrentUserClaims
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * @CurrentUser 어노테이션에 관련된 resolver 입니다.
 */
@Component
class CurrentUserResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CurrentUserClaims::class.java)
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AccessTokenClaims {
        return webRequest.getAttribute("AccessTokenClaims", 0) as AccessTokenClaims
    }
}
