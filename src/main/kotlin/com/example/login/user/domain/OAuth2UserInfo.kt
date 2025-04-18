package com.dooribun.auth.user.domain

interface OAuth2UserInfo {
    val provider: OAuth2Provider
    val attributes: Map<String, Any>
    val id: String
    val email: String
    val name: String
    val firstName: String
    val lastName: String
    val nickname: String
    val profileImageUrl: String
    fun toEntity(): User {
//        return User.of(this) ?: throw IllegalArgumentException("User entity cannot be null")
        return User.of(this)
    }
}