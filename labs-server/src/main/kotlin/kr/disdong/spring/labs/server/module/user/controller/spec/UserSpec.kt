package kr.disdong.spring.labs.server.module.user.controller.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import kr.disdong.spring.labs.auth.module.kakao.dto.AccessTokenClaims
import kr.disdong.spring.labs.common.dto.LabsResponse
import kr.disdong.spring.labs.server.module.user.dto.SignupBody
import kr.disdong.spring.labs.server.module.user.dto.UserResponse
import java.util.UUID

@Tag(name = "유저")
interface UserSpec {

    @Operation
    fun getByUserId(
        @Parameter(schema = Schema(hidden = true))
        claims: AccessTokenClaims
    ): LabsResponse<UserResponse>

    @Operation(
        summary = "회원가입 (개인정보 입력)",
    )
    fun signup(signupId: UUID, request: SignupBody): LabsResponse<Unit>
}
