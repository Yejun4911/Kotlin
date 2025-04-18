package com.dooribun.auth.user.domain.oauth2.kakao

import com.dooribun.auth.user.domain.OAuth2Provider
import com.dooribun.auth.user.domain.OAuth2UserInfo

class KakaoOAuth2UserInfo (
    attributes: Map<String, Any>
) : OAuth2UserInfo {
    // kakao_account와 profile을 안전하게 추출
    private val kakaoAccount = attributes["kakao_account"] as? Map<String, Any> ?: emptyMap()
    private val kakaoProfile = kakaoAccount["profile"] as? Map<String, Any> ?: emptyMap()

    override val provider: OAuth2Provider = OAuth2Provider.KAKAO
    override val id: String = (attributes["id"] as? Long)?.toString() ?: ""
    override val email: String = kakaoAccount["email"] as? String ?: ""
    override val name: String = ""  // 카카오에서 따로 제공 안함
    override val firstName: String = "" // 마찬가지
    override val lastName: String = "" // 마찬가지
    override val nickname: String = kakaoProfile["nickname"] as? String ?: ""
    override val profileImageUrl: String = kakaoProfile["profile_image_url"] as? String ?: ""

    // attributes는 카카오 프로필 정보 + id, email 넣어서 정리
    override val attributes: Map<String, Any> = buildMap {
        putAll(kakaoProfile)
        put("id", id)
        put("email", email)
    }
}