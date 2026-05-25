package com.kotlin.study.repository

import com.kotlin.study.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GroupRepository : JpaRepository<Group, Long> {
    @Query("select g from Group g join UserGroupMapping ugm on g.id = ugm.groupId where ugm.userId = :userId")
    fun findAllByUserId(userId: Long): List<Group>
}