package com.spacexdata.api.services.data


import com.spacexdata.api.notificationappkotlin.log.Utils.Companion.getLogger
import com.spacexdata.api.services.launch.LaunchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class LoaderServiceImpl : Loader {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var launchService: LaunchService

    override fun loadData(numberOfRecords: Int) {
        logger.info("Start - loading data")

        launchService.generateDummyData(numberOfRecords)

        logger.info("End of request for loading data - this does not wait for the data to be leaded")
    }

    override fun cleanDataBase() {
        launchService.deleteAllLaunches()
    }
}