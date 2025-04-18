package com.example.login.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecuityConfig {

    @Bean
    fun securityFilterChain(http:HttpSecurity):SecurityFilterChain {
        http
            .csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .formLogin(Customizer.withDefaults())
            .authorizeHttpRequests { authorizeRequest ->
                authorizeRequest
                    .requestMatchers(
                        AntPathRequestMatcher.antMatcher("/auth/**")
                    ).authenticated()
                    .requestMatchers(
                        AntPathRequestMatcher.antMatcher("/h2-console/**")
                    ).permitAll()
            };

        return http.build()
    }
}