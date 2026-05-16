package com.kotlin.study.service

import com.kotlin.study.controller.dto.UserCreateRequestDTO
import com.kotlin.study.controller.dto.UserResponseDTO
import com.kotlin.study.entity.User
import com.kotlin.study.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun create(userDTO: UserCreateRequestDTO) {
        requireNotNull(userDTO.name) { "name is null" }
        requireNotNull(userDTO.phoneNumber) { "phoneNumber is null" }

        userRepository.save(User(
            userDTO.name,
            userDTO.phoneNumber
        ))
    }

    fun findAll(): List<UserResponseDTO> {
        return userRepository.findAll()
            .map { user ->
                val id = requireNotNull(user.id)
                val name = requireNotNull(user.name)
                val phoneNumber = requireNotNull(user.phoneNumber)

                UserResponseDTO(id, name, phoneNumber)
            }
    }
}