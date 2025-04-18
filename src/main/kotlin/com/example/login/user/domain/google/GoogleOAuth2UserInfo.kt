package com.dooribun.auth.user.domain.oauth2.google

import com.dooribun.auth.user.domain.OAuth2Provider
import com.dooribun.auth.user.domain.OAuth2UserInfo

class GoogleOAuth2UserInfo(
    override val attributes: Map<String, Any>
) : OAuth2UserInfo {
    override val provider: OAuth2Provider = OAuth2Provider.GOOGLE
    override val id: String = attributes["sub"] as? String ?: ""
    override val email: String = attributes["email"] as? String ?: ""
    override val name: String = attributes["name"] as? String ?: ""
    override val firstName: String = attributes["given_name"] as? String ?: ""
    override val lastName: String = attributes["family_name"] as? String ?: ""
    override val nickname: String = "" // 구글에서는 기본적으로 nickname 없음
    override val profileImageUrl: String = attributes["picture"] as? String ?: ""
}