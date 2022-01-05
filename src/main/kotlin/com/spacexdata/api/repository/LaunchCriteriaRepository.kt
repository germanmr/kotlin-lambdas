package com.spacexdata.api.repository

import com.spacexdata.api.domain.Launch
import com.spacexdata.api.domain.LaunchStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root
import kotlin.reflect.KProperty1

fun <T, V> Root<T>.get(prop: KProperty1<T, V>): Path<V> = this.get(prop.name)

fun LocalDateTime.asDate(): Date = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())

@Service
class LaunchCriteriaRepository : BaseRepository<Launch>() {

    override val resourceClass: Class<Launch> get() = Launch::class.java

    fun find(id: String?, status: LaunchStatus?, fromDate: LocalDateTime?, toDate: LocalDateTime?): List<Launch> =
        criteria { query, entity ->
            query
                .addWhere(equal(entity.get(Launch::id), id), addWhen = id != null)
                .addWhere(equal(entity.get(Launch::status), status), addWhen = status != null)
                .addWhere(lessThan(entity.get(Launch::date_utc), fromDate?.asDate()), addWhen = fromDate != null)
                .addWhere(greaterThan(entity.get(Launch::date_utc), toDate?.asDate()), addWhen = toDate != null)
        }.resultList
}