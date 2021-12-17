package com.spacexdata.api.notificationappkotlin.repositories

import com.spacexdata.api.notificationappkotlin.domain.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long>