package com.kotlin.study.component

import com.kotlin.study.component.dto.SMSMessageRequestDTO
import org.springframework.stereotype.Component
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class SMSMessageQueue {
    private val queue: Queue<SMSMessageRequestDTO> = ConcurrentLinkedQueue()

    fun insert(message: SMSMessageRequestDTO) {
        queue.offer(message)
    }

    fun getOne(): SMSMessageRequestDTO? {
        return queue.poll()
    }
}