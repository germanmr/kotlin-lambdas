package com.spacexdata.api.notificationappkotlin.domain

interface HasAdaptedMessage {
    fun getAdaptedMessageByMedia(favoriteMedia: Medias): String
}