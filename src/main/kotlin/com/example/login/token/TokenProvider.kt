package com.dooribun.auth.token

import com.dooribun.auth.redis.entity.Token
import com.dooribun.auth.token.service.TokenService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class TokenProvider (
    private val tokenService: TokenService
){
    private val key: String = "ZVc3Z0g4bm5TVzRQUDJxUXBIOGRBUGtjRVg2WDl0dzVYVkMyWWs1Qlk3NkZBOXh1UzNoRWUzeTd6cVdEa0x2eQo="
    private var secretKey: Key? = null
    private val log: Logger = LoggerFactory.getLogger(JwtUtil::class.java)
    private val accessTokenExpireTime: Long = 1000 * 60 * 30L
    private val refreshTokenExpireTime: Long = 1000 * 60 * 60L * 24 * 7
    private val keyRole = "role"

    @PostConstruct
    private fun setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key))
    }
    internal fun generateAccessToken(authentication: Authentication): String {
        return generateToken(authentication, accessTokenExpireTime)
    }
    fun generateRefreshToken(authentication: Authentication, accessToken: String): String {
        val refreshToken = generateToken(authentication, refreshTokenExpireTime)
        tokenService.saveOrUpdate(authentication.name, refreshToken, accessToken) // redis에 저장
        return refreshToken;
    }

    private fun generateToken(authentication: Authentication, expireTime: Long): String {
        val now: Date = Date()
        val expiredDate: Date = Date(now.time + expireTime)

        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining())

        return Jwts.builder()
            .setSubject(authentication.name)
            .setClaims(Jwts.claims().setSubject(authentication.name))
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .signWith(secretKey)
            .compact()
    }
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = parseClaims(token)
        val authorities: List<SimpleGrantedAuthority> = getAuthorities(claims)

        val principal: User = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }
    private fun getAuthorities(claims: Claims): List<SimpleGrantedAuthority> {
        return Collections.singletonList(
            SimpleGrantedAuthority(
                claims[keyRole].toString()
            )
        )
    }
    fun reissueAccessToken(accessToken: String): String? {
        if (StringUtils.hasText(accessToken)) {
            val token: Token = tokenService.findByAccessTokenOrThrow(accessToken)
            val refreshToken: String = token.refreshToken

            if (validateToken(refreshToken)) {
                val reissueAccessToken = generateAccessToken(getAuthentication(refreshToken))
                tokenService.updateToken(reissueAccessToken, token)
                return reissueAccessToken
            }
        }
        return null
    }
    private fun validateToken(token: String?): Boolean {
        if (!StringUtils.hasText(token)) {
            return false
        }

        val claims = parseClaims(token!!)
        return claims.expiration.after(Date())
    }
    private fun parseClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: MalformedJwtException) {
            throw Exception("Token expired", e)
        } catch (e: SecurityException) {
            throw Exception("Token expired", e)
        }
    }
}