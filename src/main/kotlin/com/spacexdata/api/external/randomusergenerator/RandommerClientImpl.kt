package com.spacexdata.api.external.randomusergenerator

import com.spacexdata.api.external.RestClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RandommerClientImpl {

    @Autowired
    lateinit var restClientService: RestClientService

    fun getRandomPassword(): String =
        restClientService
            .getSomething<String>("Text/Password?length=10&hasDigits=true&hasUppercase=true&hasSpecial=false")
            .toString()

    fun getRandomName(): String =
        restClientService.getCollection("Name?nameType=surname&quantity=1")[0]

    fun getRandomArticle(): String =
        restClientService
            .getSomething<String>("Text/LoremIpsum?loremType=business&type=words&number=12")
            .toString()
            .removeSpecialCharacters()
            .replace(" ", "-")
}

private fun String.removeSpecialCharacters(): String =
    "[^A-Za-z0-9 ]".toRegex().replace(this, "")


