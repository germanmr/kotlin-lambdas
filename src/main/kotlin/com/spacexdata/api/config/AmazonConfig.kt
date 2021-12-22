package com.spacexdata.api.config

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonConfig {

    @Bean
    fun amazonS3(): AmazonS3 =
        AmazonS3Client
            .builder()
            .build()
}