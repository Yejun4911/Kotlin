package com.dooribun.auth.user.domain

enum class Role(val key: String, val title: String) {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자")
}