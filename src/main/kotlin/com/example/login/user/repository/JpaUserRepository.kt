package com.dooribun.auth.user.repository

import com.dooribun.auth.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}