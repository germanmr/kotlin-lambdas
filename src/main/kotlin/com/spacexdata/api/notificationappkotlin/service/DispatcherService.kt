package com.spacexdata.api.notificationappkotlin.service

interface DispatcherService {
    fun dispatch(batchSize: Long)
}
