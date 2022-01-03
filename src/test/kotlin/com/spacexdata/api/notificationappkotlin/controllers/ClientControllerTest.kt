package com.spacexdata.api.notificationappkotlin.controllers

import com.spacexdata.api.config.Profiles.TEST
import com.spacexdata.api.notificationappkotlin.controllers.dto.ComplexClientDTO
import com.spacexdata.api.notificationappkotlin.domain.Medias
import com.spacexdata.api.notificationappkotlin.external.RestTemplateService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
// We must use this for injecting dependencies, if not we get a "not initialize on lateinit vars"
@ExtendWith(MockKExtension::class)
@ActiveProfiles(TEST)
class ClientControllerTest {

    @MockK
    lateinit var restTemplateService: RestTemplateService

    @InjectMockKs
    lateinit var clientController: ClientController

    @Test
    fun successfully_get_getClientFromExternalSource_test() {
        // Given
        val clientID = 123L

        every {
            restTemplateService.getComplexClient(clientID).body
        } answers {
            ComplexClientDTO(
                "This is the description for the complex client",
                0,
                "Mister important",
                Medias.MAIL,
                "someemail@gmail.com"
            )
        }

        // When
        val expectedResponse = clientController.getClientFromExternalSource(clientID)

        // Then
        verify(exactly = 1) { restTemplateService.getComplexClient(123L) }

        Assertions.assertThat(
            expectedResponse ==
                    ResponseEntity.ok(
                        ComplexClientDTO(
                            "This is the description for the complex client",
                            0,
                            "Mister important", Medias.MAIL,
                            "someemail@gmail.com"
                        )
                    )
        )
    }

    // Using backticks and infix functions
    // https://medium.com/@fanisveizis/how-expressive-can-kotlin-code-be-aka-fun-with-backticks-infixes-and-extensions-f730cdf45311
    @Test
    fun `We do not find a client so returns 404`() {

        // Given
        val clientID = 321L
        // Cumbersome usage of double quote scape and line returns
        val mockBody = "{\n" +
                "\"message\" : \"The client was not found\"\n" +
                "}"

        every {
            restTemplateService.getComplexClient(clientID)
        } answers {
            ResponseEntity(
                mockBody, HttpStatus.NOT_FOUND
            )
        }

        // When
        val response = clientController.getClientFromExternalSource(clientID)

        // Then
        // We do not need to scape double quotes and line returns
        val body = """
                "message" : ""The client was not found"
                }
                """.trimIndent()
        Assertions.assertThat(
            response == ResponseEntity(
                body, HttpStatus.NOT_FOUND
            )
        )
    }

}