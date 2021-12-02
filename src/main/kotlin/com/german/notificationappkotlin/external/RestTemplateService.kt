package com.german.notificationappkotlin.external

import com.german.notificationappkotlin.config.NotificationAppConfiguration
import com.german.notificationappkotlin.controllers.dto.ComplexClientDTO
import com.german.notificationappkotlin.log.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class RestTemplateService {

    // Class properties
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var notificationAppConfiguration: NotificationAppConfiguration

    @Autowired
    lateinit var restTemplate: RestTemplate

    /**
     * https://www.fabrizioduroni.it/2020/12/23/rest-template-webclient-spring-boot/
     */
    fun getComplexClient(clientID: Long): ResponseEntity<*> = try {
        restTemplate.getForEntity(
            UriComponentsBuilder
                .fromHttpUrl(notificationAppConfiguration.url)
                .path("/client/$clientID")
                .build()
                .toUri(),
            ComplexClientDTO::class.java
        )
    } catch (e: RestClientResponseException) {
        logger.error("There was an error while consuming the service. Error: $e")
        ResponseEntity.status(e.rawStatusCode).build<Int>()
    }
}