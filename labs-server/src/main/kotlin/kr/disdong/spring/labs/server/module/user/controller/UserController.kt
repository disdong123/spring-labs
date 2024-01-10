package kr.disdong.spring.labs.server.module.user.controller

import kr.disdong.spring.labs.auth.core.annotation.AuthGuard
import kr.disdong.spring.labs.auth.core.annotation.CurrentUserClaims
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.common.dto.LabsResponse
import kr.disdong.spring.labs.server.module.user.controller.spec.UserSpec
import kr.disdong.spring.labs.server.module.user.dto.UserPersonalInfoBody
import kr.disdong.spring.labs.server.module.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class UserController(
    private val userService: UserService,
) : UserSpec {

    @GetMapping("/users/me")
    @AuthGuard
    override fun getByUserId(
        @CurrentUserClaims claims: AccessTokenClaims,
    ) = LabsResponse.of(userService.getByUserId(claims.id))

    @PatchMapping("/users/{signupId}")
    fun updatePersonalInfo(
        @PathVariable signupId: UUID,
        @RequestBody request: UserPersonalInfoBody,
    ) = LabsResponse.of(userService.updatePersonalInfo(signupId, request))
}
