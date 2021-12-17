package com.spacexdata.api.notificationappkotlin.service

import com.spacexdata.api.notificationappkotlin.domain.Client
import com.spacexdata.api.notificationappkotlin.domain.MessageRequest
import com.spacexdata.api.notificationappkotlin.domain.MessageStates
import com.spacexdata.api.notificationappkotlin.domain.Publication
import com.spacexdata.api.notificationappkotlin.exceptions.MessageRequestNotFoundException
import com.spacexdata.api.notificationappkotlin.log.Utils
import com.spacexdata.api.notificationappkotlin.repositories.ClientRepository
import com.spacexdata.api.notificationappkotlin.repositories.MessageRequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class MessageRequestService {

    // Class properties
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var clientRepository: ClientRepository

    @Autowired
    lateinit var messageRequestRepository: MessageRequestRepository

    @Transactional
    fun saveData(clients: List<Client>, publication: Publication) {
        logger.info("Start - Saving Data")
        clients.forEach {
            logger.info("Saving client: $it")
            val client = clientRepository.save(it)

            logger.info("Creating message request with saved client: $client")
            val messageRequest = MessageRequest(id = 0, client = client, publication = publication)
            logger.info("Message request created: $messageRequest")
            messageRequestRepository.save(messageRequest)
        }
        logger.info("End - Saving Data")
    }

    /**
     * Example of method that does return value and fires Exceptions!
     * We use Exceptions and the Error handler
     * 1) Entity not FOUND              ->  Fire Exception
     * 2) Entity not PENDING state      ->  Fire Exception
     * 3) Correct deletion              ->  Do not return any value
     */
    fun deleteMessageRequest(messageRequestId: Long) {

        val foundMessageRequest = messageRequestRepository.findByIdOrNull(messageRequestId)

        foundMessageRequest
            ?: throw MessageRequestNotFoundException("The request with ID: $messageRequestId is nonexistent")

        check(foundMessageRequest.messageState == MessageStates.PENDING) {
            "Error on request id: $messageRequestId, only request in State PENDING can be deleted"
        }

        messageRequestRepository.delete(foundMessageRequest)
    }

    // Returns a result and does NOT fire any Exceptions
    //                          Easy approach , only two results, can or can´t cancel
    /**
     * This method cancels the entity or returns null if the message request was not found
     * Take into account this comment when consuming it!
     */
    fun cancelMessageRequest(messageRequestId: Long): MessageRequest? {

        // We should not use this because we have the method
        val foundMessageRequest = messageRequestRepository.findByIdOrNull(messageRequestId)

        // We have to use this
        foundMessageRequest
            ?.apply {
                foundMessageRequest.messageState = MessageStates.CANCELLED
                messageRequestRepository.save<MessageRequest>(foundMessageRequest)
            }
        return foundMessageRequest
    }
}