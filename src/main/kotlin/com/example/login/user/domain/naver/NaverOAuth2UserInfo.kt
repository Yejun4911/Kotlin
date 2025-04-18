package com.dooribun.auth.user.domain.oauth2.naver

import com.dooribun.auth.user.domain.OAuth2Provider
import com.dooribun.auth.user.domain.OAuth2UserInfo

class NaverOAuth2UserInfo (
    attributes: Map<String, Any>
) : OAuth2UserInfo {
    // Naver의 response 내부에 사용자 정보가 있음
    private val response = attributes["response"] as? Map<String, Any> ?: emptyMap()

    override val provider: OAuth2Provider = OAuth2Provider.NAVER
    override val id: String = response["id"] as? String ?: ""
    override val email: String = response["email"] as? String ?: ""
    override val name: String = response["name"] as? String ?: ""
    override val firstName: String = ""
    override val lastName: String = ""
    override val nickname: String = response["nickname"] as? String ?: ""
    override val profileImageUrl: String = response["profile_image"] as? String ?: ""

    override val attributes: Map<String, Any> = response
}