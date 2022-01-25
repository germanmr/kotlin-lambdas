package com.spacexdata.api.domain

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.GenericGenerator
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
data class Launch(
    @Embedded
    val links: Links,
    val success: Boolean,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date_utc: Date = Date(),
    val status: LaunchStatus,
    @Id
    @GenericGenerator(
        name = "sequence_spacex_launch_id",
        strategy = "com.spacexdata.api.domain.SpaceXIdentifierGenerator"
    )
    @GeneratedValue(generator = "sequence_spacex_launch_id")
    val id: String
)

// Custom identifier to simulate spacex real API identifier
class SpaceXIdentifierGenerator : IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable =
        UUID.randomUUID().toString().replace("-", "")
}