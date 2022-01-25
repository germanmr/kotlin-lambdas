package com.spacexdata.api.external

import com.spacexdata.api.config.Profiles
import com.spacexdata.api.config.SpaceXConfiguration
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate


@SpringBootTest
@ExtendWith(MockKExtension::class)
@ActiveProfiles(Profiles.TEST)
class RestClientServiceTest {

    companion object {
        const val FAKE_PATH = "/url/path?variable=value"
        const val SOME_RETURNED_STRING = "SomeReturnedValue"
    }

    @InjectMockKs
    lateinit var restClientService: RestClientService

    @MockK
    lateinit var spaceXConfiguration: SpaceXConfiguration

    @MockK
    lateinit var restTemplate: RestTemplate

    @MockK
    lateinit var apiKeyProperty: ApiKeyProperty

    @Test
    fun `successfully get Response`() {

        every {
            restTemplate.exchange(
                any<String>(),
                any(),
                any(),
                any<Class<String>>()
            ).body!!
        } answers {
            SOME_RETURNED_STRING
        }

        every { spaceXConfiguration.url } answers { "http://www.asd.com/" }
        every { apiKeyProperty.api_key } answers { "374865fbs78vv78v89777f789vd879fv78f" }

        val returned = restClientService.get<String>(FAKE_PATH)

        assertEquals(returned, SOME_RETURNED_STRING)
    }

    @Test
    fun `Error thrown exception Exception`() {

        val expectedException = Exception("message")

        every {
            restTemplate.exchange(
                any<String>(),
                any(),
                any(),
                any<Class<String>>()
            )
        } throws expectedException

        every { spaceXConfiguration.url } answers { "http://www.asd.com/" }
        every { apiKeyProperty.api_key } answers { "374865fbs78vv78v89777f789vd879fv78f" }

        val thrownException = assertThrows<Exception> {
            restClientService.get<String>(FAKE_PATH)
        }

        assertEquals(expectedException, thrownException)
    }
}