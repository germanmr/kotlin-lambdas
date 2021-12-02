package com.german.notificationappkotlin.domain

import javax.persistence.Entity

@Entity
class ComplexClient(
    val description: String,
    id: Long,
    name: String,
    favoriteMedia: Medias,
    favoriteMediaIdentifier: String
) : Client(
    id,
    name,
    favoriteMedia,
    favoriteMediaIdentifier
)