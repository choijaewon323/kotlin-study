package com.kotlin.study.repository

import com.kotlin.study.entity.UserGroupMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserGroupMappingRepository : JpaRepository<UserGroupMapping, Long> {
    fun findByUserIdAndGroupId(userId: Long, groupId: Long): UserGroupMapping?

    @Query("select count(ugm) from UserGroupMapping ugm where ugm.groupId = :groupId")
    fun countByGroupId(groupId: Long): Long
}