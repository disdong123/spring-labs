package kr.disdong.spring.labs.server.core.config

import kr.disdong.spring.labs.auth.core.resolver.CurrentUserResolver
import kr.disdong.spring.labs.server.core.interceptor.auth.AuthKakaoInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var authKakaoInterceptor: AuthKakaoInterceptor

    @Autowired
    private lateinit var currentUserResolver: CurrentUserResolver

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(currentUserResolver)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authKakaoInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/api/docs.html")
            .excludePathPatterns("/api/swagger-ui/index.html")
    }
}
