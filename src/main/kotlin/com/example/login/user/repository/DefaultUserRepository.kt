package com.dooribun.auth.user.repository

import com.dooribun.auth.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class DefaultUserRepository(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository {
    // Implement the methods from UserRepository interface
    // For example:
    // override fun findById(id: Long): User? {
    //     // Implementation here
    // }
    override fun save(user: User): User {
       return jpaUserRepository.save(user);
    }
    override fun findByEmail(email: String): User? {
        return jpaUserRepository.findByEmail(email)
    }

}