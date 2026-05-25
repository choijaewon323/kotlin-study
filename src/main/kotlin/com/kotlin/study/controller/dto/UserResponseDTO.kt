package com.kotlin.study.controller.dto

data class UserResponseDTO(
    val userId: Long,
    val name: String,
    val phoneNumber: String,
    val lat: Double? = null,
    val lng: Double? = null
)
