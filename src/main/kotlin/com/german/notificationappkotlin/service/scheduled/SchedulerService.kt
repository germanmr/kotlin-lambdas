package com.german.notificationappkotlin.service.scheduled

import com.german.notificationappkotlin.controllers.dto.ComplexClientDTO
import com.german.notificationappkotlin.controllers.dto.SimpleClientDTO
import com.german.notificationappkotlin.domain.Medias
import com.german.notificationappkotlin.service.DispatcherService
import com.german.notificationappkotlin.service.validating.ValidatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService {

    @Autowired
    lateinit var validatingService: ValidatingService

    @Autowired
    @Qualifier("emailDispatcherServiceImpl")
    lateinit var dispatcher: DispatcherService

    @Scheduled(fixedDelay = 5000)
    fun validateClient() {
        val clients = listOf(
            SimpleClientDTO(1, "German", Medias.MAIL, "german@email.com"),
            ComplexClientDTO("This is a text describing the client complexity", 2, "Pete", Medias.SMS, "036659845")
        ).forEach {
            // TODO if client not valid send email notifying this
            println("Is client valid: ${validatingService.isClientValid(it)}")
        }
    }

    // TODO use coroutines for concurrent dispatching
//    @Scheduled(fixedDelay = 1000)
    fun dispatch() {
        // TODO if process activated or if there are any messages?
        dispatcher.dispatch(1L)
    }
}