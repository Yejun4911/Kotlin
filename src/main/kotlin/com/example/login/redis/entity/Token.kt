package com.dooribun.auth.redis.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.stereotype.Indexed

@RedisHash(value = "jwt", timeToLive = 60L * 60 * 24 * 7)
class Token (
    @Id
    val id: String,
    var refreshToken: String,
    var accessToken: String

) {
    fun updateRefreshToken(refreshToken: String): Token {
        this.refreshToken = refreshToken
        return this
    }

    fun updateAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }
}
