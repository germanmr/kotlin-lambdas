package com.spacexdata.api.notificationappkotlin.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.spacexdata.api.notificationappkotlin.controllers.dto.map.toDTO
import com.spacexdata.api.notificationappkotlin.log.Utils
import com.spacexdata.api.notificationappkotlin.repositories.MessageRequestRepository
import com.spacexdata.api.notificationappkotlin.service.publisher.KotlinProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AbstractDispatcherServiceImpl : DispatcherService {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var messageRequestRepository: MessageRequestRepository

    private val mapper by lazy { ObjectMapper() }

    @Autowired
    lateinit var kotlinProducer: KotlinProducer


    // TODO Add exception handling!!
    @Transactional
    override fun dispatch(batchSize: Long) {
        logger.info("Begin batch publish - batch size: $batchSize");
        val actualBatchSize = 1L
        messageRequestRepository.getBatchForUpdateById(actualBatchSize)
            .forEach {
                try {
                    logger.info("Begin message publishing: ${it.id}");
                    it.setRequestBeginProcessing()
                    messageRequestRepository.save(it)

                    logger.info("#### -> Dispatching message -> $it")
                    val messageRequestDTO = it.toDTO()
                    val message = mapper.writeValueAsString(messageRequestDTO)
                    kotlinProducer.send(message)
                    logger.info("End Publish message: ${it.id}")
                } catch (e: Exception) {
                    logger.error("#### -> There was an error sending the message -> ${e.message}")
                }
            }
    }
}