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
    lateinit var api_key_container: ApiKeyProperty

    @Autowired
    lateinit var spaceXConfiguration: SpaceXConfiguration

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun <T : Any> get(path: String) = getReified<Any>(path)

    private inline fun <reified T : Any> getReified(path: String): T = try {
        restTemplate.exchange(
            spaceXConfiguration.url + path,
            HttpMethod.GET,
            getHttpRequestWithHeaders(),
            T::class.java
        ).body!!
    } catch (e: RestClientResponseException) {
        logger.error("There was an error while consuming the service. Error: $e")
        throw Exception(e.message)
    }

    fun getCollection(path: String): Array<String> = try {
        restTemplate.exchange(
            spaceXConfiguration.url + path,
            HttpMethod.GET,
            getHttpRequestWithHeaders(),
            Array<String>::class.java
        ).body!!
    } catch (e: RestClientResponseException) {
        logger.error("There was an error while consuming the service. Error: $e")
        throw Exception(e.message, e)
    }

    private fun getHttpRequestWithHeaders(): HttpEntity<Void> {
        val headers = HttpHeaders()
        headers.set("X-API-KEY", api_key_container.api_key)
        return HttpEntity<Void>(headers)
    }
}