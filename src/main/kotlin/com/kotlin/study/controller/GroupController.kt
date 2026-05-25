package com.kotlin.study.controller

import com.kotlin.study.common.CommonResponse
import com.kotlin.study.controller.dto.GroupCreateRequestDTO
import com.kotlin.study.controller.dto.GroupJoinRequestDTO
import com.kotlin.study.controller.dto.GroupResponseDTO
import com.kotlin.study.service.GroupService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/group")
@RestController
class GroupController(
    private val groupService: GroupService
) {
    @PostMapping
    fun create(@RequestBody groupCreateRequestDTO: GroupCreateRequestDTO): CommonResponse<Boolean> {
        groupService.create(groupCreateRequestDTO)

        return CommonResponse.success(true)
    }

    @PostMapping("/join")
    fun joinGroup(@RequestBody groupJoinRequestDTO: GroupJoinRequestDTO): CommonResponse<Boolean> {
        val result = groupService.join(groupJoinRequestDTO)

        return CommonResponse.success(result)
    }

    @GetMapping("/list")
    fun findAllGroups(): CommonResponse<List<GroupResponseDTO>> {
        val results = groupService.findAllGroup()

        return CommonResponse.success(results)
    }

    @GetMapping("/join/list/{userId}")
    fun findAllGroupsByUserId(@PathVariable userId: Long): CommonResponse<List<GroupResponseDTO>> {
        val results = groupService.findAllJoinedGroup(userId)

        return CommonResponse.success(results)
    }

    @DeleteMapping("/{userId}/{groupId}")
    fun leaveOrDeleteGroup(@PathVariable userId: Long, @PathVariable groupId: Long): CommonResponse<Boolean> {
        groupService.leaveOrDeleteGroup(userId, groupId)

        return CommonResponse.success(true)
    }
}