package com.german.notificationappkotlin.service

import com.german.notificationappkotlin.domain.Client
import com.german.notificationappkotlin.domain.Medias
import com.german.notificationappkotlin.domain.MessageRequest
import com.german.notificationappkotlin.domain.Publication
import com.german.notificationappkotlin.repositories.MessageRequestRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@Rollback
@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class MessageRequestServiceTest {

    @MockK
    lateinit var messageRequestRepository: MessageRequestRepository

    @InjectMockKs
    lateinit var messageRequestService: MessageRequestService

    @Test
    fun successfullySaveData_withOne_Test() {
        // Given
        val uuid = UUID.randomUUID()
        val createdRequest = MessageRequest(
            id = 0, uuid = uuid,
            client = Client(0, "German", Medias.MAIL, "german@email.com"),
            publication = Publication("This is the message to send to German")
        )
        val slot = slot<MessageRequest>()
        every { messageRequestRepository.save<MessageRequest>(capture(slot)) } returnsArgument 0

        // When
        messageRequestService.saveData(
            arrayListOf(Client(0, "German", Medias.MAIL, "german@email.com")),
            Publication("This is the message to send to German")
        )

        // Then
        Assertions.assertThat(slot.captured.id).isEqualTo(createdRequest.id)
        Assertions.assertThat(slot.captured.client).isEqualTo(createdRequest.client)
        Assertions.assertThat(slot.captured.publication).isEqualTo(createdRequest.publication)

    }

    @Test
    fun successfullyNotSaveData_asNoDataIsSent_Test() {

        val messageRequestRepository = mockk<MessageRequestRepository>()
        MockKAnnotations.init(this)

        messageRequestService.saveData(emptyList(), Publication(""))

        verify(exactly = 0) { messageRequestRepository.save(ArgumentMatchers.any()) }
    }
}