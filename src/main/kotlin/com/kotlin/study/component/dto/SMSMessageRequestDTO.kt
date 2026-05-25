package com.kotlin.study.component.dto

data class SMSMessageRequestDTO(
    val from: String,
    val to: String,
    val content: String
)
