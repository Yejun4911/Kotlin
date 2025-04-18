package com.dooribun.auth.config

import com.dooribun.auth.handler.OAuth2AuthenticationSuccessHandler
import com.dooribun.auth.user.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
class SecurityConfig (
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler
) {
    companion object {
        private val AUTH_WHITELIST = arrayOf(
            "/api/users/**", // Allows unrestricted access to this endpoint
        )
    }
    @Bean
    fun filterChain(http:HttpSecurity): SecurityFilterChain {
        http
            .csrf{
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // #1
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("*").permitAll()  //USER 역할만 접근 허용
            }
            .oauth2Login {
                it.userInfoEndpoint { userInfo -> userInfo.userService(customOAuth2UserService) }
                it.successHandler(oAuth2AuthenticationSuccessHandler)
            }
        return http.build();
    }

}