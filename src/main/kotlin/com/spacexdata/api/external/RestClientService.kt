package com.spacexdata.api.external

import com.spacexdata.api.config.SpaceXConfiguration
import com.spacexdata.api.notificationappkotlin.log.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate

@Service
class RestClientService {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var spaceXConfiguration: SpaceXConfiguration

    @Autowired
    lateinit var restTemplate: RestTemplate

    private inline fun <reified T : Any> get(path: String): T = try {
        val headers = HttpHeaders()
        headers.set("X-API-KEY", "8c3c4ca0f5c24e9e96fc2d96159bcd80")
        val requestEntity: HttpEntity<Void> = HttpEntity<Void>(headers)
        restTemplate.exchange(
            spaceXConfiguration.url + path, HttpMethod.GET, requestEntity, T::class.java, null
        ).body!!
    } catch (e: RestClientResponseException) {
        logger.error("There was an error while consuming the service. Error: $e")
        throw Exception(e.message)
    }

    fun <T : Any> getSomething(path: String) = get<Any>(path)

    fun getCollection(path: String): Array<String> = try {
        val headers = HttpHeaders()
        headers.set("X-API-KEY", "8c3c4ca0f5c24e9e96fc2d96159bcd80")
        val requestEntity: HttpEntity<Void> = HttpEntity<Void>(headers)
        restTemplate.exchange(
            spaceXConfiguration.url + path, HttpMethod.GET, requestEntity, Array<String>::class.java, null
        ).body!!
    } catch (e: RestClientResponseException) {
        logger.error("There was an error while consuming the service. Error: $e")
        throw Exception(e.message, e)
    }
}