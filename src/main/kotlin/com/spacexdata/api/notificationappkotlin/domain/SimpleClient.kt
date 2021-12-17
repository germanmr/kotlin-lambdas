package com.spacexdata.api.notificationappkotlin.domain

import javax.persistence.Entity

@Entity
class SimpleClient(
    okey: Boolean,
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