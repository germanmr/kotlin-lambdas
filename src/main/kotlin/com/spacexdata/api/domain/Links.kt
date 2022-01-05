package com.spacexdata.api.domain

import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class Links(
    @Embedded
    val patch: Patch,
    val webcast: String,
    val article: String,
    val wikipedia: String,
)