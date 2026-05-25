package com.kotlin.study.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    name: String,
    phoneNumber: String,
    lat: Double = 0.0,
    lng: Double = 0.0,
) {
    @Id @GeneratedValue
    @Column(name = "user_id")
    var id: Long? = null

    var name: String? = name

    @Column(name = "phone_number")
    var phoneNumber: String? = phoneNumber

    var lat: Double? = lat

    var lng: Double? = lng
}