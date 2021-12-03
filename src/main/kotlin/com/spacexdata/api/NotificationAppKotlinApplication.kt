package com.spacexdata.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@EnableKafka
//@EnableScheduling
class NotificationAppKotlinApplication

fun main(args: Array<String>) {
    runApplication<NotificationAppKotlinApplication>(*args)
}