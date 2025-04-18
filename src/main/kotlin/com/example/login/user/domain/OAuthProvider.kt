package com.dooribun.auth.user.domain

enum class OAuth2Provider(val registrationId: String) {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    NAVER("naver"),
    KAKAO("kakao");

    companion object {
        fun fromRegistrationId(registrationId: String): OAuth2Provider? {
            return entries.find { it.registrationId == registrationId }
        }
    }
}