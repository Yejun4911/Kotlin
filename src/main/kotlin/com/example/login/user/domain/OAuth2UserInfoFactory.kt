package com.dooribun.auth.user.domain

import com.dooribun.auth.user.domain.oauth2.google.GoogleOAuth2UserInfo
import com.dooribun.auth.user.domain.oauth2.kakao.KakaoOAuth2UserInfo
import com.dooribun.auth.user.domain.oauth2.naver.NaverOAuth2UserInfo

object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(
        registrationId: String,
        attributes: Map<String, Any>
    ): OAuth2UserInfo {
        return when (registrationId) {
            OAuth2Provider.GOOGLE.registrationId -> GoogleOAuth2UserInfo(attributes)
            OAuth2Provider.NAVER.registrationId -> NaverOAuth2UserInfo(attributes)
            OAuth2Provider.KAKAO.registrationId -> KakaoOAuth2UserInfo(attributes)
            else -> {throw Exception("Unsupported provider: $registrationId")}
        }
    }
}