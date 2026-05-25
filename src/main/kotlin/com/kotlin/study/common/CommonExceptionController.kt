package com.kotlin.study.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionController {
    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception) : ResponseEntity<CommonResponse<String>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CommonResponse.fail(exception))
    }
}