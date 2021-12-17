package com.spacexdata.api.controllers

import com.spacexdata.api.domain.dto.LaunchDTO
import com.spacexdata.api.domain.dto.LinksDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URL
import java.util.*

@RestController
@RequestMapping("launches")
class LaunchesController {

    private val list = listOf(
        LaunchDTO(
            LinksDTO(
                URL("https://images2.imgbox.com/53/22/dh0XSLXO_o.png"),
                URL("https://youtu.be/1MkcWK2PnsU"),
                URL("https://spaceflightnow.com/2020/03/07/late-night-launch-of-spacex-cargo-ship-marks-end-of-an-era/"),
                URL("https://en.wikipedia.org/wiki/SpaceX_CRS-20"),
            ),
            true,
            "CRS-20",
            Date(),
            "5eb87d42ffd86e000604b384"
        )
    )

    @GetMapping("")
    fun get(): List<LaunchDTO> {
        return list
    }
}