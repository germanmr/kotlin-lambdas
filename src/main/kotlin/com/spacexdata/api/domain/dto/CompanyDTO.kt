package com.spacexdata.api.domain.dto

data class CompanyDTO(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launch_sites: Int,
    val valuation: Long
)
