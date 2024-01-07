package kr.disdong.spring.labs.server.module.user.controller.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.common.dto.LabsResponse
import kr.disdong.spring.labs.domain.module.user.model.User

@Tag(name = "유저")
interface UserSpec {

    @Operation
    fun getByUserId(claims: AccessTokenClaims): LabsResponse<User>
}
