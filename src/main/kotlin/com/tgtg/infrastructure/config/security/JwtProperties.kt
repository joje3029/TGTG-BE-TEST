package com.tgtg.infrastructure.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.jwt")
data class JwtProperties(
    val secret: String,    // JWT 서명에 사용할 비밀키
    val expiration: Long   // 토큰 만료 시간
) 