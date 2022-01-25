package com.spacexdata.api.external.randomusergenerator

import com.spacexdata.api.config.Profiles
import com.spacexdata.api.external.RestClientService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ExtendWith(MockKExtension::class)
@ActiveProfiles(Profiles.TEST)
class RandommerClientImplTest {

    companion object {
        const val RANDOM_PASSWORD = "98r8787gfd"
        const val RANDOM_NAME = "SomeName"
        const val RANDOM_ARTICLE = "this-is-a-title-for-an-article"
    }

    @MockK
    lateinit var restClientService: RestClientService

    @InjectMockKs
    lateinit var testedClass: RandommerClientImpl

    @Test
    fun `successfully get a random password`() {

        every {
            restClientService.get<String>(any())
        } answers {
            RANDOM_PASSWORD
        }

        val returnedPassword = runBlocking { testedClass.getRandomPassword() }

        assertEquals(returnedPassword, RANDOM_PASSWORD)
    }

    @Test
    fun `successfully get a random name`() {

        every {
            restClientService.getCollection(any())
        } answers {
            arrayOf(RANDOM_NAME)
        }

        val returned = runBlocking { testedClass.getRandomName() }

        assertEquals(returned, RANDOM_NAME)
    }

    @Test
    fun `successfully get a random article`() {

        every {
            restClientService.get<String>(any())
        } answers {
            "this is a title for an article"
        }

        val returned = runBlocking { testedClass.getRandomArticle() }

        assertEquals(returned, RANDOM_ARTICLE)
    }
}