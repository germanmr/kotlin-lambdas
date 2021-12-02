package com.german.notificationappkotlin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
//https://clientvalidationservice.free.beeceptor.com/client/123
@ConfigurationProperties(prefix = "clientvalidationservice.free.beeceptor.com")
class NotificationAppConfiguration {
    var url: String = "https://clientvalidationservice.free.beeceptor.com"
}