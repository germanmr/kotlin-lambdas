package com.aws.blog.jvmlangs.kotlin

import com.amazonaws.regions.Regions
import com.amazonaws.services.autoscaling.AmazonAutoScaling
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder
import com.amazonaws.services.autoscaling.model.UpdateAutoScalingGroupRequest
import com.amazonaws.services.ecs.AmazonECSClientBuilder
import com.amazonaws.services.ecs.model.UpdateServiceRequest
import com.amazonaws.services.rds.AmazonRDSClientBuilder
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest
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

    fun handler(input: InputStream, output: OutputStream) {
        val inputObj = mapper.readValue<HandlerInput>(input)

        try {
            shutdownEC2InstancesByLoweringAutoscalingCapacity()

            shutdownEC2InstancesByLoweringNumberOfTasksInECSService()

            stopRDSByInstanceName(inputObj)

            mapper.writeValue(output, "Process finished correctly")

        } catch (e: Exception) {
            mapper.writeValue(
                output, """
                    Process finished with Error:
                        ${e.message?.let { HandlerOutput(it) }}
                """.trimIndent()
            )
        }
    }

    private fun stopRDSByInstanceName(inputObj: HandlerInput) {

        try {
            require(inputObj.dbInstance.isNotEmpty()) { "DB Instance must have a value" }
            require(inputObj.action.isNotEmpty()) { "Action must have a value" }

            val dbInstanceIdentifier = inputObj.dbInstance

            // Check if action is valid
            val action = try {
                Actions.valueOf(inputObj.action)
            } catch (e: IllegalArgumentException) {
                throw Exception("Invalid action for value: ${inputObj.action}")
            }

            // Get an RDS client for a specific region
            val rdsClient = AmazonRDSClientBuilder.standard().withRegion(Regions.US_EAST_2).build()

            // Check if instance exists
            val describeDBInstancesRequest = DescribeDBInstancesRequest().withDBInstanceIdentifier(dbInstanceIdentifier)
            val describeDBInstances = rdsClient.describeDBInstances(describeDBInstancesRequest)
            if (describeDBInstances.dbInstances.isEmpty()) {
                throw Exception("Invalid db instance for value: ${inputObj.dbInstance}")
            }

            // Stop the requested instance
            val stopDBInstanceRequest = StopDBInstanceRequest().withDBInstanceIdentifier(dbInstanceIdentifier)
            val stopDBInstance = rdsClient.stopDBInstance(stopDBInstanceRequest)

            println("Successfully stopped instance $stopDBInstance")
        } catch (e: Exception) {
            throw Exception(
                """
                        Failure while performing the action: ${inputObj.action} on instance:${inputObj.dbInstance}
            ${e.message}
            """
            )
        }
    }

    private fun shutdownEC2InstancesByLoweringAutoscalingCapacity() {

        // Update Autoscaling Group to stop all related EC2 Instances
        // Desired Capacity = 0 and Maxsize = 0
        val client: AmazonAutoScaling = AmazonAutoScalingClientBuilder.standard().build()
        val autoScalingGroupName = "EC2ContainerService-LaunchesCluster-EcsInstanceAsg-1CZDS3H95PDHT"
        val request: UpdateAutoScalingGroupRequest = UpdateAutoScalingGroupRequest()
            .withAutoScalingGroupName(autoScalingGroupName)
            .withDesiredCapacity(0)
            .withMaxSize(0)
        client.updateAutoScalingGroup(request)
    }

    private fun shutdownEC2InstancesByLoweringNumberOfTasksInECSService() {

        // Setting number of tasks to Zero, to stop all tasks of the service services
        // val client = AmazonECSClientBuilder.standard().build()
        val client = AmazonECSClientBuilder.standard().withRegion(Regions.US_EAST_2).build()
        val request =
            UpdateServiceRequest().withCluster("LaunchesCluster").withService("launches_service").withDesiredCount(0)
        client.updateService(request)
    }
}
