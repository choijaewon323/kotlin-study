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
    phoneNumber: String
) {
    @Id @GeneratedValue
    @Column(name = "user_id")
    var id: Long? = null

    var name: String? = name

    @Column(name = "phone_number")
    var phoneNumber: String? = phoneNumber
}