package com.german.notificationappkotlin.domain

import javax.persistence.Embeddable

@Embeddable
data class Publication(val message: String) : HasAdaptedMessage {
    override fun getAdaptedMessageByMedia(favoriteMedia: Medias): String {
        return when (favoriteMedia) {
            Medias.SMS -> message.take(254)
            else -> message
        }
    }
}