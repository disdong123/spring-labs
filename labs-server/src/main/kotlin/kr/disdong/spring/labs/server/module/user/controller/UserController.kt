package kr.disdong.spring.labs.server.module.user.controller

import kr.disdong.spring.labs.common.dto.LabsResponse
import kr.disdong.spring.labs.server.module.user.controller.spec.UserSpec
import kr.disdong.spring.labs.server.module.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) : UserSpec {

    @GetMapping("/users/{userId}")
    override fun getByUserId(
        @PathVariable userId: Long,
    ) = LabsResponse.of(userService.getByUserId(userId))
}
