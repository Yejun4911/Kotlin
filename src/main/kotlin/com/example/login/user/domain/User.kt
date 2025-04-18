package com.dooribun.auth.user.domain

import com.dooribun.auth.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false, unique = true)
    val email: String,


    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    val nickname: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,
    @Column()
    var profileImageUrl: String,
    @Column(nullable = false)
    val loginId: String,
    @Column(nullable = false)
    val provider: OAuth2Provider,
) : BaseEntity() {
    companion object {
        fun of(userInfo: OAuth2UserInfo): User {
            return User(
                email = userInfo.email,
                name = userInfo.name,
                nickname = userInfo.nickname.ifBlank { userInfo.name },
                role = Role.USER,
                profileImageUrl = userInfo.profileImageUrl,
                loginId = "${userInfo.provider.name.lowercase()}_${userInfo.id}",
                provider = userInfo.provider,
                id = 0L
            )
        }
    }
    fun update(name: String, profileImageUrl: String): User {
        this.name = name
        this.profileImageUrl = profileImageUrl
        return this
    }
    fun getRoleKey(): String {
        return this.role.key
    }
}