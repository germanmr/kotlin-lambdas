package com.german.notificationappkotlin.controllers

import com.german.notificationappkotlin.controllers.dto.ComplexClientDTO
import com.german.notificationappkotlin.controllers.dto.map.toDTO
import com.german.notificationappkotlin.controllers.dto.map.toEntity
import com.german.notificationappkotlin.external.RestTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("clients")
class ClientController {

    @Autowired
    lateinit var restTemplateService: RestTemplateService

    // TODO move this into a service class
    @GetMapping("/external/{clientID}")
    fun getClientFromExternalSource(@PathVariable("clientID") clientID: Long): ResponseEntity<Any> {
        // First we have to find the client
        val restCallReply = restTemplateService.getComplexClient(clientID).body

        return if (restCallReply is ComplexClientDTO) {
            println(
                """
                obtainedComplexClientDTO:
                $restCallReply"
            """.trimIndent()
            )
            val complexClient = restCallReply.toEntity()

            println("complexClient : $complexClient")
            val complexClientDTO = complexClient.toDTO()

            println("Returning : $complexClientDTO")
            ResponseEntity.ok(complexClientDTO)
        } else {
            ResponseEntity(restCallReply, HttpStatus.NOT_FOUND)
        }
    }
}