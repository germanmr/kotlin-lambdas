package com.german.notificationappkotlin.service.validating

import com.german.notificationappkotlin.controllers.dto.ClientDTO
import com.german.notificationappkotlin.controllers.dto.ComplexClientDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ValidatingService {

    @Autowired
    lateinit var externalValidationClient: ExternalValidationClient

    fun isClientValid(client: ClientDTO): Boolean {
        return if (client is ComplexClientDTO) {
            externalValidationClient.requestValidation(client.description)
        } else
            true
    }
}