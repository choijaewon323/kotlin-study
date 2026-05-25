package com.kotlin.study.controller.dto

data class UserLocationResponseDTO(
    val groupId: Long,
    val locations: List<UserLocationDTO>
)

data class UserLocationDTO(
    val name: String,
    val lat: Double?,
    val lng: Double?
)
