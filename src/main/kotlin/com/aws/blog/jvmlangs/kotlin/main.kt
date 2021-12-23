package com.aws.blog.jvmlangs.kotlin

import com.amazonaws.regions.Regions
import com.amazonaws.services.rds.AmazonRDSClientBuilder
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest
import com.amazonaws.services.rds.model.DescribeSourceRegionsRequest
import com.amazonaws.services.rds.model.StopDBInstanceRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.io.OutputStream

data class HandlerInput(val dbInstance: String, val action: String)
data class HandlerOutput(val message: String)

enum class Actions { START, STOP }

class Main {

    private val mapper = jacksonObjectMapper()

    fun handler(input: InputStream, output: OutputStream): Unit {
        val inputObj = mapper.readValue<HandlerInput>(input)

        try {
            require(!inputObj.dbInstance.isNullOrEmpty()) { "DB Instance must have a value" }
            require(!inputObj.action.isNullOrEmpty()) { "Action must have a value" }

            val dbInstanceIdentifier = inputObj.dbInstance

            // Check if action is valid

            val action = try {
                Actions.valueOf(inputObj.action)
            } catch (e: IllegalArgumentException) {
                mapper.writeValue(
                    output,
                    HandlerOutput("Invalid action for value: ${inputObj.action}")
                )
                return
            }

            // Get an RDS client for a specific region
            val rdsClient = AmazonRDSClientBuilder.standard().withRegion(Regions.US_EAST_2).build()

            // This should be in our region
            // Get our region
            val describeSourceRegions = DescribeSourceRegionsRequest().withRegionName(Regions.US_EAST_2.toString())
            val regions = rdsClient.describeSourceRegions(describeSourceRegions)

            // Check if instance exists
            val describeDBInstancesRequest = DescribeDBInstancesRequest().withDBInstanceIdentifier(dbInstanceIdentifier)
            val describeDBInstances = rdsClient.describeDBInstances(describeDBInstancesRequest)
            if (describeDBInstances.dbInstances.isEmpty()) {
                mapper.writeValue(
                    output,
                    HandlerOutput("Invalid db instance for value: ${inputObj.dbInstance}")
                )
                return
            }

            // Stop the requested instance
            val stopDBInstanceRequest = StopDBInstanceRequest().withDBInstanceIdentifier(dbInstanceIdentifier)
            val stopDBInstance = rdsClient.stopDBInstance(stopDBInstanceRequest)

            println("Successfully stopped instance $stopDBInstance")

            mapper.writeValue(
                output,
                HandlerOutput("Successfully performed action: ${inputObj.action} on instance ${inputObj.dbInstance}")
            )

        } catch (e: Exception) {
            mapper.writeValue(
                output,
                HandlerOutput(
                    """
                    Failure while performing the action: ${inputObj.action} on instance:${inputObj.dbInstance}
                    ${e.message}
                    """
                )
            )
        }

    }
}
