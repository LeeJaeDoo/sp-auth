package com.sp.application

import com.sp.application.extensions.*
import com.sp.application.model.*
import com.sp.domain.*
import com.sp.presentation.request.*
import org.apache.commons.codec.binary.*
import org.springframework.stereotype.*

/**
 * @author Jaedoo Lee
 */
@Service
class TokenCommandService(
    private val tokenServices: TokenService
) {
    fun createToken(request: LoginRequest): String {
        return tokenServices.createAccessToken(request.toMemberInfo())
    }

    fun getAuthentication(tokenValue: String): LoginMemberInfo {
        return String(Base64.decodeBase64(tokenServices.getPayload(tokenValue))).toModel()
    }
}
