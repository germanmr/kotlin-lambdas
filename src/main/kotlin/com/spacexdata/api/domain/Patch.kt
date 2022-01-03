package com.spacexdata.api.domain

import javax.persistence.Embeddable

@Embeddable
data class Patch(val small: String)