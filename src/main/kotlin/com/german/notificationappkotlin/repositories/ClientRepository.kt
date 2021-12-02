package com.german.notificationappkotlin.repositories

import com.german.notificationappkotlin.domain.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client, Long>