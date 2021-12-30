package com.spacexdata.api.exception

class ImageUploadException: Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}