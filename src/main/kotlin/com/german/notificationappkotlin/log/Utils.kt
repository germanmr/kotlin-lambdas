package com.german.notificationappkotlin.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Utils {
    companion object {
        fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)
    }
}