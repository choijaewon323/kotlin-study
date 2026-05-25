package com.kotlin.study.component

import com.kotlin.study.component.dto.SMSMessageRequestDTO
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class SMSMessageQueue {
    private val queue: Queue<SMSMessageRequestDTO> = ConcurrentLinkedQueue()
    private val dlq: Queue<SMSMessageRequestDTO> = ConcurrentLinkedQueue()

    fun insert(message: SMSMessageRequestDTO) {
        queue.offer(message)
    }

    fun getOne(): SMSMessageRequestDTO? {
        return queue.poll()
    }

    fun insertFailedMessage(message: SMSMessageRequestDTO) {
        dlq.offer(message)
    }

    @Scheduled(fixedDelay = 60000)
    fun switch() {
        dlq.forEach {
            insert(it)
        }

        dlq.clear()
    }
}