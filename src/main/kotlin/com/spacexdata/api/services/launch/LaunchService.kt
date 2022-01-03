package com.spacexdata.api.services.launch

interface LaunchService {
    fun generateDummyData(numberOfRecords: Int = 50)
    fun deleteAllLaunches()
}