package com.spacexdata.api.controllers

import com.spacexdata.api.services.data.LoaderServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/data")
class LoaderController {

    @Autowired
    lateinit var loaderServiceImpl: LoaderServiceImpl

    @PutMapping(value = ["/load"])
    fun loadData(
        @RequestParam(name = "numberOfRecords", required = false, defaultValue = "50") numberOfRecords: Int
    ) {
        loaderServiceImpl.loadData(numberOfRecords)
    }

    @DeleteMapping("/clean/all")
    fun cleanDataBase() {
        loaderServiceImpl.cleanDataBase()
    }
}