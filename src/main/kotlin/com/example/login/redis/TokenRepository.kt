package com.dooribun.auth.redis

import com.dooribun.auth.redis.entity.Token
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface TokenRepository : CrudRepository<Token, String> {
    fun findByAccessToken(accessToken: String?): Optional<Token>
}