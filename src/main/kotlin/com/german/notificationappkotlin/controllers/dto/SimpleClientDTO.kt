package com.german.notificationappkotlin.controllers.dto

import com.german.notificationappkotlin.domain.Medias

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