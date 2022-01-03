package com.spacexdata.api.services.data

interface Loader {
    fun loadData(numberOfRecords: Int = 50)
    fun cleanDataBase()
}