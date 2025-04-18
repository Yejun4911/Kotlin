package com.dooribun.auth.user.service

import com.dooribun.auth.user.domain.OAuth2UserInfo
import com.dooribun.auth.user.domain.OAuth2UserInfoFactory
import com.dooribun.auth.user.domain.OAuth2UserPrincipal
import com.dooribun.auth.user.domain.User
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import kotlin.math.log


@Service
class CustomOAuth2UserService(
    private val userService: UserService,
): DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val originAttributes = oAuth2User.attributes // OAuth2User의 attribute
        val registrationId = userRequest.clientRegistration.registrationId // 소셜 정보를 가져옵니다.

        val attributes: OAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, originAttributes);
        val userNameAttributeName = userRequest.clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName // OAuth 로그인
        val user: User = saveOrUpdate(attributes)


        return OAuth2UserPrincipal(user,originAttributes,userNameAttributeName);
    }
    private fun saveOrUpdate(authAttributes: OAuth2UserInfo): User {
        val user = userService.findByEmail(authAttributes.email)
            ?.copy(name = authAttributes.name, profileImageUrl = authAttributes.profileImageUrl)
            ?: authAttributes.toEntity()
        return userService.save(user)
    }
}