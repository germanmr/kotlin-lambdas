package com.spacexdata.api.services.launch

import com.spacexdata.api.domain.Launch
import com.spacexdata.api.domain.Links
import com.spacexdata.api.domain.Patch
import com.spacexdata.api.external.randomusergenerator.RandommerClientImpl
import com.spacexdata.api.notificationappkotlin.log.Utils
import com.spacexdata.api.repository.LaunchRepository
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random.Default.nextBoolean
import kotlin.random.Random.Default.nextInt
import kotlin.system.measureTimeMillis

@Service
class LaunchServiceImpl : LaunchService {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var launchRepository: LaunchRepository

    @Autowired
    lateinit var randommerClient: RandommerClientImpl

    @Autowired
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override fun deleteAllLaunches() {
        // We´re using deleteAll() instead of a Query because we might add inheritance,
        // which would required is to delete more than one table. This way we can leave this work to hibernate
        launchRepository.deleteAll()

        // We can get the first record in order to see if they were actually deleted
        if (launchRepository.findAll(Pageable.ofSize(1)).totalElements > 0L) {
            throw Exception("Records were not deleted")
        }
    }

    @Async
    override fun generateDummyData(numberOfRecords: Int) {
        createRecordsAsync(numberOfRecords)
    }

    private fun createRecordsAsync(numberOfRecords: Int) = runBlocking {
        repeat(numberOfRecords) {
            launch(coroutineDispatcher) {
                createRecordAsync()
            }
        }
        logger.info("Finished ten Launches!")
    }

    private suspend fun createRecordAsync() = coroutineScope {
        logger.info("Starting search with coroutines list")
        val tasks = listOf(
            async(coroutineDispatcher) { getExternalRandomPatchSmall() },
            async(coroutineDispatcher) { getExternalRandomWebcast() },
            async(coroutineDispatcher) { getExternalRandomName() }
        )

        var returnedValues: List<String>

        logger.info("Start awaiting")
        val timeMillis = measureTimeMillis { returnedValues = tasks.awaitAll() }

        logger.info("Took: $timeMillis")

        returnedValues.forEach {
            logger.info(it)
        }
        logger.info("All coroutines finished!")

        val small = returnedValues[0]
        val webcast = returnedValues[1]
        val name = returnedValues[2]
        logger.info("Getting article!")
        val article = getExternalRandomArticle()

        val wikipedia = "https://en.wikipedia.org/wiki/${name}"
        logger.info("Creating record!")
        launchRepository.save(
            Launch(Links(Patch(small), webcast, article, wikipedia), nextBoolean(), name, Date(), "")
        )
    }

    suspend fun getExternalRandomPatchSmall(): String {
        val filename = getExternalRandomString()
        return "https://images2.imgbox.com/${nextInt()}/${nextInt()}/${filename}.png"
    }

    fun getExternalRandomArticle(): String {
        val title = getRandomArticleTitle()
        val date = getRandomDate()

        return "https://spaceflightnow.com/$date/$title"
    }

    private fun getRandomArticleTitle(): String = randommerClient.getRandomArticle()

    private fun getRandomDate(): String {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        var date = LocalDate.parse(
            "${nextInt(from = 2021, until = 2030)}/${nextInt(from = 1, until = 12).toString().padStart(2, '0')}/${
                nextInt(
                    from = 1,
                    until = 27
                ).toString().padStart(2, '0')
            }", formatter
        )
        return formatter.format(date)
    }

    suspend fun getExternalRandomWebcast(): String {
        val id = getExternalRandomString()
        return "https://youtu.be/${id}"
    }

    suspend fun getExternalRandomString(): String = randommerClient.getRandomPassword()

    suspend fun getExternalRandomName(): String = "${randommerClient.getRandomName()}-${nextInt()}"

}