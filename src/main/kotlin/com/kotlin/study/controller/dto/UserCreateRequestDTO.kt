package com.kotlin.study.controller.dto

data class UserCreateRequestDTO(
    val name: String?,
    val phoneNumber: String?
) {
    fun validate() {
        requireNotNull(name) { "Name is required" }
        requireNotNull(phoneNumber) { "PhoneNumber is required" }
    }
}
