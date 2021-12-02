package com.german.notificationappkotlin.domain

interface HasAdaptedMessage {
    fun getAdaptedMessageByMedia(favoriteMedia: Medias): String
}