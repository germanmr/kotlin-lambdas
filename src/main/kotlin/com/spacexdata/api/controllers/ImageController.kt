package com.spacexdata.api.controllers

import com.spacexdata.api.handler.ImageStorageHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/images")
class ImageController {

    @Autowired
    private lateinit var imageStorageHandler: ImageStorageHandler

    @PostMapping("/upload")
    fun upload(multipartFile: MultipartFile): ResponseEntity<Void> {
        imageStorageHandler.upload(multipartFile)
        return ResponseEntity(HttpStatus.OK)
    }
}