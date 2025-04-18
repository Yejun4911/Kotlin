package com.dooribun.auth.user.repository

import com.dooribun.auth.user.domain.User
import java.util.Optional

interface UserRepository {
//    fun findById(id: Long): User?
    fun findByEmail(email: String): User?
    fun save(user: User): User
//    fun deleteById(id: Long)
//    fun existsByEmail(email: String): Boolean
}