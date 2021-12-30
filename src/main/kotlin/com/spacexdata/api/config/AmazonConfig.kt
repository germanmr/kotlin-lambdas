package com.spacexdata.api.config

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.spacexdata.api.notificationappkotlin.config.Profiles.TEST
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AmazonConfig {

    @Bean
    @Profile("!$TEST")
    fun amazonS3(): AmazonS3 =
        AmazonS3Client
            .builder()
            .build()

    @Bean
    @Profile(TEST)
    fun testAmazonS3(): AmazonS3 =
        AmazonS3Client
            .builder()
            .withRegion(Regions.US_EAST_2)
            .build()
}