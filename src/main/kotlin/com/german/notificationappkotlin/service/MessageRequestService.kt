package com.german.notificationappkotlin.service

import com.german.notificationappkotlin.domain.Client
import com.german.notificationappkotlin.domain.MessageRequest
import com.german.notificationappkotlin.domain.MessageStates
import com.german.notificationappkotlin.domain.Publication
import com.german.notificationappkotlin.exceptions.MessageRequestNotFoundException
import com.german.notificationappkotlin.repositories.ClientRepository
import com.german.notificationappkotlin.repositories.MessageRequestRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class MessageRequestService {
    private val logger = LoggerFactory.getLogger(MessageRequestService::class.java)

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