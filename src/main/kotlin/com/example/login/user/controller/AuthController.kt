package com.dooribun.auth.user.controller

import com.dooribun.auth.token.service.TokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController("/api/v1/auth")
class AuthController(
    private val tokenService: TokenService
) {
    @GetMapping("/success")
    fun loginSuccess(): String {
        return "success"
    }
}