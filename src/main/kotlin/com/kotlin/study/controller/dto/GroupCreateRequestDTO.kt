package com.kotlin.study.controller.dto

data class GroupCreateRequestDTO(
    val name: String,
    val limit: Int,
    val createdUserId: Long,
)
