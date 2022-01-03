package com.spacexdata.api.repository

import com.spacexdata.api.domain.Launch
import org.springframework.data.jpa.repository.JpaRepository

interface LaunchRepository : JpaRepository<Launch, String>