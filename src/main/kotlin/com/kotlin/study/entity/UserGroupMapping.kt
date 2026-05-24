package com.kotlin.study.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "user_group_mapping")
@Entity
class UserGroupMapping(
    userId: Long,
    groupId: Long,
    userStatus: UserStatus,
) {
    @Id
    @GeneratedValue
    @Column(name = "user_group_mapping_id")
    var id: Long? = null

    var userId: Long? = userId

    var groupId: Long? = groupId

    var userStatus: String? = userStatus.name
}