package com.spacexdata.api.notificationappkotlin.controllers.dto

import com.spacexdata.api.notificationappkotlin.domain.Medias

class SimpleClientDTO(
    id: Long,
    name: String,
    favoriteMedia: Medias,
    favoriteMediaIdentifier: String
) : ClientDTO(
    id,
    name,
    favoriteMedia,
    favoriteMediaIdentifier
)