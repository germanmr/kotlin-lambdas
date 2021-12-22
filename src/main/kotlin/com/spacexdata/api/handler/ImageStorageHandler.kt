package com.spacexdata.api.handler

import com.spacexdata.api.exception.ImageUploadException
import org.springframework.web.multipart.MultipartFile

interface ImageStorageHandler {

    @Throws(ImageUploadException::class)
    fun upload(multipartFile: MultipartFile)
}