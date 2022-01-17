package com.spacexdata.api.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.spacexdata.api.domain.LaunchStatus
import java.util.*

class LaunchDTO(
    val links: LinksDTO,
    val success: Boolean,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date_utc: Date,
    val status: LaunchStatus,
    val id: String
)