package com.kotlin.study.controller.dto

data class UserLocationUpdateRequestDTO(
    val userId: Long,
    val lat: Double,
    val lng: Double
)
