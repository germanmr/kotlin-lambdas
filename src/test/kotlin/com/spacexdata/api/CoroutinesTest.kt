package com.spacexdata.api

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CoroutinesTest {

    @Test
    fun `should cancel printing World`() {
        val result = runBlocking {
            val job = launch {
                delay(5000)
                println("World")
            }
            println("Hello")
            job.cancel()
            println("After cancellation")
            "Result"
        }
        println(result)
    }

}