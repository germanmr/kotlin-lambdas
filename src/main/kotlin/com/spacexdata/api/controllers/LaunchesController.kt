package com.spacexdata.api.controllers

import com.spacexdata.api.domain.LaunchStatus
import com.spacexdata.api.domain.dto.LaunchDTO
import com.spacexdata.api.services.launch.LaunchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("launches")
class LaunchesController {

    @Autowired
    lateinit var launchService: LaunchService

    @GetMapping("")
    fun get(
        @RequestParam(name = "id", required = false) id: String?,
        @RequestParam(name = "status", required = false) status: LaunchStatus?,
        @RequestParam(name = "fromDate", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        fromDate: LocalDateTime?,
        @RequestParam(name = "toDate", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        toDate: LocalDateTime?
    ): List<LaunchDTO> {
        return launchService.getLaunchesByCriteria(id, status, fromDate, toDate)
    }
}