package com.spacexdata.api.notificationappkotlin.exceptions.handler

import com.spacexdata.api.notificationappkotlin.controllers.response.MessageRequestErrorResponse
import com.spacexdata.api.notificationappkotlin.controllers.response.RequestErrorResponse
import com.spacexdata.api.notificationappkotlin.exceptions.ClientNotFoundException
import com.spacexdata.api.notificationappkotlin.exceptions.MessageRequestNotFoundException
import com.spacexdata.api.notificationappkotlin.log.Utils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GenericExceptionHandler {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    @ExceptionHandler
    fun handleException(exception: MessageRequestNotFoundException): ResponseEntity<MessageRequestErrorResponse> {
        logger.error("Error: $exception")
        val messageRequestErrorResponse = MessageRequestErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.message.orEmpty(),
            System.currentTimeMillis()
        )
        return ResponseEntity(messageRequestErrorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleException(exception: ClientNotFoundException): ResponseEntity<MessageRequestErrorResponse> {
        logger.error("Error: $exception")
        val messageRequestErrorResponse = MessageRequestErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.message.orEmpty(),
            System.currentTimeMillis()
        )
        return ResponseEntity(messageRequestErrorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleGenericException(exception: Exception): ResponseEntity<RequestErrorResponse> {
        logger.error("Error: $exception")
        val requestErrorResponse = RequestErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.message.orEmpty(),
            System.currentTimeMillis()
        )
        return ResponseEntity(requestErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}