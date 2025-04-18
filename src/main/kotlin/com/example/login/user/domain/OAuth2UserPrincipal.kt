package com.dooribun.auth.user.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class OAuth2UserPrincipal(
    private val user: User,
    private val attributes: Map<String, Any>,
    private val attributeKey: String
) : OAuth2User, UserDetails {

    override fun getPassword(): String? = null

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getAuthorities(): Collection<GrantedAuthority> = Collections.singletonList(
        SimpleGrantedAuthority(user.getRoleKey())
    );

    override fun getName(): String = attributes[attributeKey].toString()
}