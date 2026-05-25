package com.kotlin.study.controller

import com.kotlin.study.common.CommonResponse
import com.kotlin.study.controller.dto.UserCreateRequestDTO
import com.kotlin.study.controller.dto.UserResponseDTO
import com.kotlin.study.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun create(@RequestBody userCreateRequestDTO: UserCreateRequestDTO): CommonResponse<Boolean> {
        userService.create(userCreateRequestDTO)

        return CommonResponse.success(true)
    }

    @GetMapping("/list")
    fun findAll(): CommonResponse<List<UserResponseDTO>> {
        val results = userService.findAll()

        return CommonResponse.success(results)
    }
}