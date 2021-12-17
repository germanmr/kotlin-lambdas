package com.spacexdata.api.notificationappkotlin.controllers.dto

import java.util.*

class MessageRequestDTO(
    val uuid: UUID,
    val client: ClientDTO,
    val publication: PublicationDTO,
    val error: String? = null,
    val messageState: String
)