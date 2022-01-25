package com.spacexdata.api.external.randomusergenerator

import com.spacexdata.api.external.RestClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RandommerClientImpl {

    @Autowired
    lateinit var restClientService: RestClientService

    suspend fun getRandomPassword(): String =
        restClientService
            .get<String>("Text/Password?length=10&hasDigits=true&hasUppercase=true&hasSpecial=false")
            .toString()

    suspend fun getRandomName(): String =
        restClientService.getCollection("Name?nameType=surname&quantity=1")[0]

    suspend fun getRandomArticle(): String =
        restClientService
            .get<String>("Text/LoremIpsum?loremType=business&type=words&number=12")
            .toString()
            .removeSpecialCharacters()
            .replace(" ", "-")
}

private fun String.removeSpecialCharacters(): String =
    "[^A-Za-z0-9 ]".toRegex().replace(this, "")


