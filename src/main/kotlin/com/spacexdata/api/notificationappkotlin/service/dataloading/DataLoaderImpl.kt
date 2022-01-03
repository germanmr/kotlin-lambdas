package com.spacexdata.api.notificationappkotlin.service.dataloading


import com.spacexdata.api.notificationappkotlin.domain.ComplexClient
import com.spacexdata.api.notificationappkotlin.domain.Medias
import com.spacexdata.api.notificationappkotlin.domain.Publication
import com.spacexdata.api.notificationappkotlin.domain.SimpleClient
import com.spacexdata.api.notificationappkotlin.log.Utils.Companion.getLogger
import com.spacexdata.api.notificationappkotlin.service.MessageRequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DataLoaderImpl : DataLoader {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @Autowired
    lateinit var messageRequestService: MessageRequestService

    override fun loadData() {
        logger.info("Start - loading data")
        val clients = arrayListOf(
            SimpleClient(false, 1, "German1", Medias.MAIL, "someemail@gmail.com"),
            ComplexClient("Description ", 2, "German2", Medias.MAIL, "someemail@gmail.com"),
            SimpleClient(true, 3, "German3", Medias.MAIL, "someemail@gmail.com"),
            ComplexClient("Description ", 4, "German4", Medias.MAIL, "someemail@gmail.com"),
            SimpleClient(false, 5, "German5", Medias.MAIL, "someemail@gmail.com"),
            ComplexClient("Description ", 6, "German6", Medias.MAIL, "someemail@gmail.com"),
            SimpleClient(true, 7, "German7", Medias.MAIL, "someemail@gmail.com"),
            ComplexClient("Description ", 8, "German8", Medias.MAIL, "someemail@gmail.com"),
            SimpleClient(false, 9, "German9", Medias.MAIL, "someemail@gmail.com"),
            ComplexClient("Description ", 10, "German10", Medias.MAIL, "someemail@gmail.com"),
            SimpleClient(true, 11, "German11", Medias.MAIL, "someemail@gmail.com"),
            ComplexClient("Description ", 12, "Peter", Medias.MAIL, "peteremail@gmail.com")
        )
        val publication = Publication("This is a the message to be send!!")

        messageRequestService.saveData(clients, publication)

        logger.info("End - loading data")
    }
}