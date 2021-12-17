package com.spacexdata.api.notificationappkotlin.domain

enum class MessageStates {
    PENDING,
    PROCESSING,
    ERROR,
    CANCELLED,
    SUCCESS;
}