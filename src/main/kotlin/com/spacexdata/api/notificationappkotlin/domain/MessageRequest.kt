package com.spacexdata.api.notificationappkotlin.domain

import java.util.*
import javax.persistence.*


@Entity
data class MessageRequest(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    val client: Client,
    @Embedded
    val publication: Publication,
    var error: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "message_state")
    var messageState: MessageStates = MessageStates.PENDING
) {

    // TODO use an entity to with Embeddable StateWithMessage?
    fun setRequestSuccess() {
        messageState = MessageStates.SUCCESS
        error = null
    }

    fun setRequestError(errorMessage: String) {
        messageState = MessageStates.ERROR
        error = errorMessage
    }

    fun setRequestBeginProcessing() {
        messageState = MessageStates.PROCESSING
        error = null
    }

    fun setRequestPending() {
        messageState = MessageStates.PENDING
        error = null
    }

// TODO
//    fun setRequestAcknowledgement(messageRequestDTO: MessageRequestDTO) {
//        if (MessageStates.SUCCESS.equals(MessageStates.valueOf(messageRequestDTO.getMessageState().toString()))) {
//            setSuccess()
//        } else {
//            setError(messageRequestDTO.getError())
//        }
//    }
}