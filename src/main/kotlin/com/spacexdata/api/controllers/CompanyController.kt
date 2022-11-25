package com.spacexdata.api.controllers

import com.spacexdata.api.domain.dto.CompanyDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("company")
class CompanyController {

    private val companyDTO = CompanyDTO(
        "SpaceX",
        "Elon Musk",
        2002,
        8000,
        3,
        52000000000
    )

    @GetMapping("")
    fun get(): CompanyDTO {
        return companyDTO
    }
}