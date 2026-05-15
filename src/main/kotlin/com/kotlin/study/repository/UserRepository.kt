package com.kotlin.study.repository

import com.kotlin.study.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>