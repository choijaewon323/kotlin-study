package com.kotlin.study.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "groups")
@Entity
class Group(
    name: String,
    limit: Int,
) {
    @Id
    @GeneratedValue
    @Column(name = "group_id")
    var id: Long? = null

    var name: String? = name

    @Column(name = "group_limit")
    var limit: Int? = limit
}