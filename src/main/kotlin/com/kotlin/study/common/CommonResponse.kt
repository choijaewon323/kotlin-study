package com.kotlin.study.common

data class CommonResponse<T>(
    val status: String,
    val message: String?,
    val data: T?
)
