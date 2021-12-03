package com.spacexdata.api.notificationappkotlin.controllers.dto

import com.spacexdata.api.notificationappkotlin.domain.Medias

abstract class ClientDTO(
    val id: Long,
    val name: String,
    val favoriteMedia: Medias,
    val favoriteMediaIdentifier: String
)
