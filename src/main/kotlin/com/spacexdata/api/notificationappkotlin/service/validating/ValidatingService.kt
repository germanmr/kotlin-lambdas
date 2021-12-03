package com.spacexdata.api.notificationappkotlin.service.validating

import com.spacexdata.api.notificationappkotlin.controllers.dto.ClientDTO
import com.spacexdata.api.notificationappkotlin.controllers.dto.ComplexClientDTO
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