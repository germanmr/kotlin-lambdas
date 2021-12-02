package com.german.notificationappkotlin.controllers

import com.german.notificationappkotlin.service.DispatcherService
import com.german.notificationappkotlin.service.MessageRequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("messages")
class MessagesRequestController {

    @Autowired
    @Qualifier("emailDispatcherServiceImpl")
    lateinit var emailDispatcherServiceImpl: DispatcherService

    @Autowired
    lateinit var messageRequestService: MessageRequestService

    // Injecting spring properties
    @Value("\${version.number}")
    lateinit var version: String

    @PutMapping("/dispatch")
    fun dispatch() {
        emailDispatcherServiceImpl.dispatch(1)
    }

    @DeleteMapping("/{id}")
    fun deleteRequestMessage(@PathVariable("id") messageRequestId: Long) {
        messageRequestService.deleteMessageRequest(messageRequestId)
    }

    @PutMapping("/cancel/{id}")
    fun cancelMessageRequest(@PathVariable("id") messageRequestId: Long): ResponseEntity<Any> {
        //
        val cancelledMessageRequest = messageRequestService.cancelMessageRequest(messageRequestId)

        // If we want 404 whenn not found we must use an if
        // We use null implicit null checking
        /**
         * WARNING: The run block will be evaluated either if b is null, or if the let-block evaluates to null.
         */
//        return cancelledMessageRequest?.let { // let: With the cancelled request I build a different object!
//            ResponseEntity.ok(cancelledMessageRequest)
//            // warning, if we "return null" from here this will enter the "run" block
//        } ?: run { // run: Just run a block of code
//            ResponseEntity("The message Request was not found with id: $messageRequestId", HttpStatus.NOT_FOUND)
//        }

        // Kotlin expressions and "if" usage
        return if (cancelledMessageRequest != null)
            ResponseEntity.ok(cancelledMessageRequest)
        else
            ResponseEntity.notFound().build()
    }

    @GetMapping("/ping")
    fun ping(): String {
        return "hello"
    }

    @GetMapping("/version")
    fun version(): String {
        return version
    }
}