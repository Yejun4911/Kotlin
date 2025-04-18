package com.dooribun.auth.user.service

import com.dooribun.auth.user.domain.User
import com.dooribun.auth.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    fun save(user: User): User {
        return userRepository.save(user)
    }
}