package com.german.notificationappkotlin.controllers.dto

import com.german.notificationappkotlin.domain.Medias

class ComplexClientDTO(
    val description: String,
    id : Long,
    name: String,
    favoriteMedia: Medias,
    favoriteMediaIdentifier: String
) : ClientDTO(
    id,
    name,
    favoriteMedia,
    favoriteMediaIdentifier
)