package com.spacexdata.api.services.launch

import com.spacexdata.api.domain.Launch
import com.spacexdata.api.domain.LaunchStatus
import com.spacexdata.api.domain.Links
import com.spacexdata.api.domain.Patch
import com.spacexdata.api.domain.dto.LaunchConverter
import com.spacexdata.api.domain.dto.LaunchDTO
import com.spacexdata.api.external.randomusergenerator.RandommerClientImpl
import com.spacexdata.api.notificationappkotlin.log.Utils
import com.spacexdata.api.repository.LaunchCriteriaRepository
import com.spacexdata.api.repository.LaunchRepository
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
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
    lateinit var launchCriteriaRepository: LaunchCriteriaRepository

    @Autowired
    lateinit var randommerClient: RandommerClientImpl

    @Autowired
    lateinit var coroutineDispatcher: CoroutineDispatcher

    @Autowired
    lateinit var launchConverter: LaunchConverter

    override fun deleteAllLaunches() {
        // We´re using deleteAll() instead of a Query because we might add inheritance,
        // which would required is to delete more than one table. This way we can leave this work to hibernate
        launchRepository.deleteAll()

        // We can get the first record in order to see if they were actually deleted
        if (launchRepository.findAll(Pageable.ofSize(1)).totalElements > 0L) {
            throw Exception("Records were not deleted")
        }
    }

    override fun getLaunchesByCriteria(
        id: String?,
        status: LaunchStatus?,
        fromDate: LocalDateTime?,
        toDate: LocalDateTime?
    ): List<LaunchDTO> {
        return launchConverter.convertToDto(launchCriteriaRepository.find(id, LaunchStatus.PENDING, fromDate, toDate))
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
        logger.info("Finished all Launches!")
    }

    @Async
    suspend fun createRecordAsync() = coroutineScope {

        // Create the entity, save it
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

        val status = getRandomStatus()
        val success = nextBoolean()
        val wikipedia = "https://en.wikipedia.org/wiki/${name}"
        val date = getRandomDate()

        val launch =
            Launch(Links(Patch(small), webcast, article, wikipedia), success, name, date, status, "")

        logger.info("Inserting record!")
        launchRepository.save(launch)
    }

    private fun getRandomStatus(): LaunchStatus = LaunchStatus.values().toList().shuffled().first()

    suspend fun getExternalRandomPatchSmall(): String {
        val filename = getExternalRandomString()
        return "https://images2.imgbox.com/${nextInt()}/${nextInt()}/${filename}.png"
    }

    suspend fun getExternalRandomArticle(): String {
        val title = getRandomArticleTitle()
        val date = getRandomDateString()

        return "https://spaceflightnow.com/$date/$title"
    }

    private suspend fun getRandomArticleTitle(): String = randommerClient.getRandomArticle()

    private fun getRandomDate(): Date {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val randomLocalDate = LocalDate.parse(
            "${nextInt(from = 2021, until = 2030)}/${nextInt(from = 1, until = 12).toString().padStart(2, '0')}/${
                nextInt(
                    from = 1,
                    until = 27
                ).toString().padStart(2, '0')
            }", formatter
        )
        return Date.from(randomLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    private fun getRandomDateString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val date = LocalDate.parse(
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