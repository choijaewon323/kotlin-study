package com.kotlin.study.service

import com.kotlin.study.component.SMSMessageQueue
import com.kotlin.study.component.dto.SMSMessageRequestDTO
import com.kotlin.study.controller.dto.GroupCreateRequestDTO
import com.kotlin.study.controller.dto.GroupJoinRequestDTO
import com.kotlin.study.controller.dto.GroupResponseDTO
import com.kotlin.study.entity.Group
import com.kotlin.study.entity.UserGroupMapping
import com.kotlin.study.entity.UserStatus
import com.kotlin.study.repository.GroupRepository
import com.kotlin.study.repository.UserGroupMappingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicBoolean

@Transactional
@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userGroupMappingRepository: UserGroupMappingRepository,
    private val userService: UserService,
    private val smsMessageQueue: SMSMessageQueue,
) {
    private val lock = Any()

    fun create(groupDTO: GroupCreateRequestDTO) {
        val savedGroup = groupRepository.save(Group(groupDTO.name, groupDTO.limit))

        check(savedGroup != null)
        val savedGroupId = requireNotNull(savedGroup.id)

        userGroupMappingRepository.save(
            UserGroupMapping(
                groupDTO.createdUserId,
                savedGroupId,
                UserStatus.ADMIN
            )
        )
    }

    fun join(groupDTO: GroupJoinRequestDTO): Boolean {
        val savedGroup = groupRepository.findByIdOrNull(groupDTO.groupId)
            ?: throw IllegalStateException("group is not found")
        val groupLimit = requireNotNull(savedGroup.limit)

        val exist = userGroupMappingRepository.findByUserIdAndGroupId(groupDTO.userId, groupDTO.groupId)

        check(exist == null) { "user is already joined" }

        val result = AtomicBoolean(false)

        synchronized(lock) {
            val currentCount = userGroupMappingRepository.countByGroupId(groupDTO.groupId)

            if (currentCount < groupLimit) {
                userGroupMappingRepository.save(UserGroupMapping(groupDTO.userId, groupDTO.groupId, UserStatus.MEMBER))

                result.set(true)
            }
        }

        if (result.get()) {
            val newUser = userService.findOne(groupDTO.userId)

            userService.findAllByGroupId(groupDTO.groupId)
                .forEach { user ->
                    smsMessageQueue.insert(
                        SMSMessageRequestDTO(
                            "010-0000-0000",
                            user.phoneNumber!!,
                            "${newUser.name} 님이 입장하셨습니다."
                        )
                    )
                }
        }

        return result.get()
    }

    @Transactional(readOnly = true)
    fun findAllGroup(): List<GroupResponseDTO> {
        return groupRepository.findAll()
            .map(this::toResponseDTO)
    }

    @Transactional(readOnly = true)
    fun findAllJoinedGroup(userId: Long): List<GroupResponseDTO> {
        return groupRepository.findAllByUserId(userId)
            .map(this::toResponseDTO)
    }

    fun leaveOrDeleteGroup(userId: Long, groupId: Long) {
        val existGroup = groupRepository.findByIdOrNull(groupId)
            ?: throw IllegalStateException("group is not found")
        val existInGroup = userGroupMappingRepository.findByUserIdAndGroupId(userId, groupId)
            ?: throw IllegalStateException("user is not in group")

        val userStatus = requireNotNull(existInGroup.userStatus)
        val usersInGroup = userService.findAllByGroupId(groupId)
        userGroupMappingRepository.deleteAllByGroupId(groupId)

        if (userStatus == UserStatus.ADMIN.name) {
            groupRepository.deleteById(existGroup.id!!)
        }

        val leaveUser = userService.findOne(userId)

        val message = if (userStatus == UserStatus.ADMIN.name) {
            "${existGroup.name} 그룹이 삭제되었습니다"
        } else {
            "${leaveUser.name}님이 그룹에서 나갔습니다."
        }

        usersInGroup.forEach {
            smsMessageQueue.insert(
                SMSMessageRequestDTO(
                    "010-0000-0000",
                    it.phoneNumber!!,
                    message
                )
            )
        }
    }

    private fun toResponseDTO(group: Group): GroupResponseDTO {
        val groupId = requireNotNull(group.id)
        val name = requireNotNull(group.name)

        return GroupResponseDTO(groupId, name)
    }
}