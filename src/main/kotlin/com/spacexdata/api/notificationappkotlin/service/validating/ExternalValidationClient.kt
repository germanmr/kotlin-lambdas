package com.spacexdata.api.notificationappkotlin.service.validating

import org.springframework.stereotype.Service

@Service
class ExternalValidationClient {
    fun requestValidation(description: String): Boolean = false
}
