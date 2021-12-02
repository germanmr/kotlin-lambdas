package com.german.notificationappkotlin.domain

enum class MessageStates {
    PENDING,
    PROCESSING,
    ERROR,
    CANCELLED,
    SUCCESS;
}