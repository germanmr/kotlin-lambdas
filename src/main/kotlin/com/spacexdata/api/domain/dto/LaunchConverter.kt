package com.spacexdata.api.domain.dto

import com.spacexdata.api.domain.Launch
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface LaunchConverter {

    fun convertToDto(launches: List<Launch>): List<LaunchDTO>
}