package com.german.notificationappkotlin.controllers.response

class MessageRequestErrorResponse(value: Int, message: String = "", currentTimeMillis: Long) :
    RequestErrorResponse(value, message, currentTimeMillis)