package com.german.notificationappkotlin.controllers.dto

import com.german.notificationappkotlin.domain.Medias

abstract class ClientDTO(
    val id : Long,
    val name: String,
    val favoriteMedia: Medias,
    val favoriteMediaIdentifier: String
)
