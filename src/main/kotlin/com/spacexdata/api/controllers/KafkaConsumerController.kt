package com.spacexdata.api.controllers

import com.spacexdata.api.domain.dto.CompanyDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.TopicPartitionOffset
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("kafka")
class KafkaConsumerController(private val kafkaTemplate: KafkaTemplate<String, String>) {


    @GetMapping("")
    fun get() {
        val topic = "quickstart-events"
        val partition = 0
        val offset = 1
        //val receive = kafkaTemplate.receive(topic, partition, offset.toLong())
//        println("Consumer message")
//        val message = receive?.value()
//        println(message)
        val receive2 = kafkaTemplate.receive(
            listOf(
                TopicPartitionOffset(topic, partition, 2.toLong()),
                TopicPartitionOffset(topic, partition, 5.toLong())
            )
        )

        receive2.forEach {
            kafkaTemplate.send("quickstart-events-new", it.value())
        }
    }
}