package com.spacexdata.api.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
// Example
// https://randommer.io/api/Text/Password?length=10&hasDigits=true&hasUppercase=true&hasSpecial=false
@ConfigurationProperties(prefix = "randommer.io")
class SpaceXConfiguration {
    var url: String = "https://randommer.io/api/"

    @Bean
    fun taskExecutor(): Executor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 50
        executor.maxPoolSize = 50
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("Asynctasks-")
        executor.initialize()
        return executor
    }

    @Bean
    fun coroutinesThreadpool(): Executor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 10
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("Coroutines-")
        executor.initialize()
        return executor
    }

    @Bean
    fun coroutineDispatcher(): CoroutineDispatcher = coroutinesThreadpool()?.asCoroutineDispatcher()!!
}