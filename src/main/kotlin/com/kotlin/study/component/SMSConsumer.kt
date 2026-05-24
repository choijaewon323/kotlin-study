package com.kotlin.study.component

import com.kotlin.study.component.dto.SMSMessageRequestDTO
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class SMSConsumer(
    private val smsMessageQueue: SMSMessageQueue,
    private val restTemplate: RestTemplate
) {
    private val smsServer = "localhost:8090"
    private val log = LoggerFactory.getLogger(SMSConsumer::class.java)

    @Async
    @Scheduled(fixedDelay = 10000)
    fun consume() {
        testConsumer().invoke()
    }

    private fun testConsumer(): () -> Unit {
        return {
            var element: SMSMessageRequestDTO? = smsMessageQueue.getOne()

            while (element != null) {
                log.info(element.toString())

                element = smsMessageQueue.getOne()
            }
        }
    }

    private fun realConsumer(): () -> Unit {
        return {
            var element: SMSMessageRequestDTO? = smsMessageQueue.getOne()

            while (element != null) {
                val responseEntity: ResponseEntity<Any> = restTemplate.postForEntity(
                    URI.create(smsServer),
                    element,
                    Any::class.java
                )

                if (!responseEntity.statusCode.is2xxSuccessful) {
                    log.warn("SMS Consumer Error: ${responseEntity.statusCode}")
                }

                element = smsMessageQueue.getOne()
            }
        }
    }
}