package com.dooribun.auth.handler

import com.dooribun.auth.token.TokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.util.*


@Component
class OAuth2AuthenticationSuccessHandler(
    private val tokenProvider: TokenProvider
): AuthenticationSuccessHandler {

    private val uri: String = "/hello"
    override fun onAuthenticationSuccess(
        request: HttpServletRequest, response: HttpServletResponse,
        authentication: Authentication
    ) {
        val accessToken = tokenProvider.generateAccessToken(authentication)
        val refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken)

        // 토큰 전달을 위한 redirect
        val redirectUrl = UriComponentsBuilder.fromUriString(uri)
            .queryParam("accessToken", accessToken)
            .queryParam("refreshToken", refreshToken)
            .build().toUriString()

        response.sendRedirect(redirectUrl)
    }

}