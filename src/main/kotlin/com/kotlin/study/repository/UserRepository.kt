package com.kotlin.study.repository

import com.kotlin.study.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    fun findByPhoneNumber(phoneNumber: String): User?

    @Query("select u from User u join UserGroupMapping ugm on u.id = ugm.userId where ugm.groupId = :groupId")
    fun findByGroupId(groupId: Long): List<User>
}