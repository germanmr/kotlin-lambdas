package com.spacexdata.api.notificationappkotlin.repositories

import com.spacexdata.api.notificationappkotlin.domain.MessageRequest
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.QueryHint
import javax.transaction.Transactional

@Repository
@Transactional
interface MessageRequestRepository : JpaRepository<MessageRequest, Long>, JpaSpecificationExecutor<MessageRequest> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun findById(id: Long): Optional<MessageRequest>

    @Query(
        value = "select * FROM message_request mr " +
                " where mr.message_state in ('PENDING','ERROR') " +
                " order by mr.id for update skip locked limit :batch_size", nativeQuery = true
    )
    @QueryHints(*[QueryHint(name = "javax.persistence.lock.timeout", value = "5000")])
    fun getBatchForUpdateById(@Param("batch_size") batch_size: Long): List<MessageRequest>

    @Query(
        value = "select * FROM message_request mr " +
                " where mr.message_state in ('PENDING','ERROR') " +
                " order by mr.id for update skip locked limit 1", nativeQuery = true
    )
    @QueryHints(*[QueryHint(name = "javax.persistence.lock.timeout", value = "5000")])
    fun getNextMessageForUpdate(): MessageRequest
}
