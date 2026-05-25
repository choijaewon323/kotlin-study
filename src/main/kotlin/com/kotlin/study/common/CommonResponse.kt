package com.kotlin.study.common

data class CommonResponse<T>(
    val status: String,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> success(data: T?): CommonResponse<T> {
            return CommonResponse("success", null, data)
        }

        fun fail(error: Exception): CommonResponse<String> {
            return CommonResponse("error", error.message, null)
        }
    }
}
