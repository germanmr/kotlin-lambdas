package com.german.notificationappkotlin.controllers.dto.map

import com.german.notificationappkotlin.controllers.dto.*
import com.german.notificationappkotlin.domain.*


fun MessageRequest.toDTO(): MessageRequestDTO = MessageRequestDTO(
    uuid = uuid,
    client = client.toDTO(),
    publication = publication.toDTO(),
    error = error,
    messageState = messageState.toString()
)

fun ComplexClientDTO.toEntity(): ComplexClient {
    return ComplexClient(description, id, name, favoriteMedia, favoriteMediaIdentifier)
}

fun ComplexClient.toDTO(): ComplexClientDTO {
    return ComplexClientDTO(description, id, name, favoriteMedia, favoriteMediaIdentifier)
}

fun SimpleClient.toDTO(): ClientDTO {
    return SimpleClientDTO(id, name, favoriteMedia, favoriteMediaIdentifier)
}

//Extension method
// Expressions in kotlin
fun Client.toDTO(): ClientDTO {
    return when (this) {
        is SimpleClient -> toDTO()
        is ComplexClient -> toDTO()
        else -> throw IllegalStateException("This is not a valid subclass")
    }
}

fun Publication.toDTO(): PublicationDTO {
    return PublicationDTO(message)
}