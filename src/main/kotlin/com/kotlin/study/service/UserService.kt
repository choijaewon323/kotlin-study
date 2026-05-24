package com.kotlin.study.service

import com.kotlin.study.controller.dto.UserCreateRequestDTO
import com.kotlin.study.controller.dto.UserResponseDTO
import com.kotlin.study.entity.User
import com.kotlin.study.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    private val userRepository: UserRepository
) {
    @PostConstruct
    fun init() {
        val testUsers = listOf(
            User("테스트 1", "010-1234-5671"),
            User("테스트 2", "010-1234-5672"),
            User("테스트 3", "010-1234-5673"),
            User("테스트 4", "010-1234-5674"),
            User("테스트 5", "010-1234-5675"),
            User("테스트 6", "010-1234-5676"),
            User("테스트 7", "010-1234-5677"),
            User("테스트 8", "010-1234-5678")
        )

        userRepository.saveAll(testUsers)
    }

    fun create(userDTO: UserCreateRequestDTO) {
        val existUser: User? = userRepository.findByPhoneNumber(userDTO.phoneNumber)

        check(existUser == null) { "user already exists" }

        val savedUser: User? = userRepository.save(User(userDTO.name, userDTO.phoneNumber))

        check(savedUser != null) { "failed to create new user" }
    }

    @Transactional(readOnly = true)
    fun findAll(): List<UserResponseDTO> {
        return userRepository.findAll()
            .map { user ->
                val id = requireNotNull(user.id)
                val name = requireNotNull(user.name)
                val phoneNumber = requireNotNull(user.phoneNumber)

                UserResponseDTO(id, name, phoneNumber)
            }
    }

    @Transactional(readOnly = true)
    fun findAllByGroupId(groupId: Long): List<User> {
        return userRepository.findByGroupId(groupId)
    }

    @Transactional(readOnly = true)
    fun findOne(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: throw IllegalStateException("user not found")
    }
}