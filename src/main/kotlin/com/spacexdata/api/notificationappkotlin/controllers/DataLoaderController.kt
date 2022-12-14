package com.spacexdata.api.notificationappkotlin.controllers

import com.spacexdata.api.notificationappkotlin.service.dataloading.DataLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dataloader")
class DataLoaderController {

    @Autowired
    lateinit var dataLoader: DataLoader

    @PutMapping("/loaddata")
    fun loadData() {
        dataLoader.loadData()
    }
}