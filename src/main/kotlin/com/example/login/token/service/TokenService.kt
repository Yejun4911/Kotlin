package com.dooribun.auth.token.service

import com.dooribun.auth.redis.TokenRepository
import com.dooribun.auth.redis.entity.Token
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenRepository: TokenRepository,
){

    fun deleteRefreshToken(userKey: String) {
        tokenRepository.deleteById(userKey)
    }
    fun findByAccessTokenOrThrow(accessToken: String): Token {
        return tokenRepository.findByAccessToken(accessToken)
            .orElseThrow { Exception("") } //TODO.
    }
    @Transactional
    fun saveOrUpdate(memberKey: String, refreshToken: String, accessToken: String) {
        val token = tokenRepository.findByAccessToken(accessToken)
            .map { it.updateRefreshToken(refreshToken) }
            .orElseGet { Token(memberKey, refreshToken, accessToken) }

        tokenRepository.save(token)
    }

    @Transactional
    fun updateToken(accessToken: String, token: Token) {
        token.updateAccessToken(accessToken)
        tokenRepository.save(token)
    }
}