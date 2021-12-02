package com.german.notificationappkotlin.domain

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Client(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
    val name: String,
    val favoriteMedia: Medias,
    val favoriteMediaIdentifier: String
)