package com.german.notificationappkotlin.controllers.response

open class RequestErrorResponse(
    var status: Int? = null,
    var message: String,
    var timeStamp: Long
)