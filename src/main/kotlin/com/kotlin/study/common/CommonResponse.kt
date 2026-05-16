package com.kotlin.study.common

data class CommonResponse<T>(
    val status: String,
    val message: String? = null,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T): CommonResponse<T> {
            return CommonResponse("success", data = data)
        }

        fun fail(exception: Exception): CommonResponse<Any> {
            return CommonResponse("error", message = exception.message)
        }
    }
}
