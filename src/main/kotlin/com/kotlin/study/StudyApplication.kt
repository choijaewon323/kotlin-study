package com.kotlin.study

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class StudyApplication

fun main(args: Array<String>) {
	runApplication<StudyApplication>(*args)
}
