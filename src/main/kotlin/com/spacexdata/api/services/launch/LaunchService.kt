package com.spacexdata.api.services.launch

import com.spacexdata.api.domain.LaunchStatus
import com.spacexdata.api.domain.dto.LaunchDTO
import java.time.LocalDateTime

interface LaunchService {
    fun generateDummyData(numberOfRecords: Int = 50)
    fun deleteAllLaunches()
    fun getLaunchesByCriteria(id: String?, status: LaunchStatus?, fromDate: LocalDateTime?, toDate: LocalDateTime?): List<LaunchDTO>
}