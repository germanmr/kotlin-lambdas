package com.spacexdata.api.handler

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import com.amazonaws.services.s3.AmazonS3

import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.model.PutObjectRequest
import com.spacexdata.api.exception.ImageUploadException
import com.spacexdata.api.notificationappkotlin.log.Utils
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.file.Files

@Component
class ImageStorageHandlerImpl : ImageStorageHandler {

    @Autowired
    lateinit var amazonS3: AmazonS3

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = Utils.getLogger(javaClass.enclosingClass)
    }

    private object Constants {
        const val AMAZON_IMAGE_BUCKET_NAME = "spacex-app-images"
    }

    /**
     * Mark this method as Async because it can be a candidate for async usage.
     * For example, saving new launch with image, where uploading image to s3 can be performed in separate thread.
     */
    @Async
    override fun upload(multipartFile: MultipartFile) {
        val file: File = convertMultiPartFileToFile(multipartFile)
        logger.info("Uploading file with name ${file.name}")
        uploadFile(file);
        deleteTemporaryFile(file)
    }

    private fun convertMultiPartFileToFile(multipartFile: MultipartFile): File {
        multipartFile.originalFilename ?: throw ImageUploadException("Multipart file has no original name")
        val file = File(multipartFile.originalFilename!!)
        try {
            FileOutputStream(file).use { outputStream -> outputStream.write(multipartFile.bytes) }
        } catch (e: FileNotFoundException) {
            throw ImageUploadException("Cannot open stream for file ${file.name}", e)
        } catch (e: SecurityException) {
            throw ImageUploadException("Cannot write to file ${file.name} due to permission issue", e)
        }
        return file
    }

    private fun uploadFile(file: File) {
        try {
            val putObjectRequest = PutObjectRequest(Constants.AMAZON_IMAGE_BUCKET_NAME, file.name, file)
            amazonS3.putObject(putObjectRequest)
        } catch (e: SdkClientException) {
            throw ImageUploadException("Error ${e.localizedMessage} occurred in the client while processing request", e)
        } catch (e: AmazonServiceException) {
            throw ImageUploadException("Error ${e.localizedMessage} occurred while uploading file to s3 bucket ${Constants.AMAZON_IMAGE_BUCKET_NAME}", e)
        }
    }

    private fun deleteTemporaryFile(file: File) {
        try {
            Files.delete(file.toPath())
        } catch (e: IOException) {
            logger.error("Error ${e.localizedMessage} occurred while deleting temporary file")
        } catch (e: SecurityException) {
            logger.error("Error ${e.localizedMessage} occurred while deleting temporary file because of lack of delete permissions")
        }
    }
}