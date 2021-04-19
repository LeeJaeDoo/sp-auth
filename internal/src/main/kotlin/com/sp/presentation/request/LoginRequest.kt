package com.sp.presentation.request

import com.sp.application.model.*

/**
 * @author Jaedoo Lee
 */
data class LoginRequest(
    val no: Long,
    val email: String,
    val nickname: String
) {
    fun toMemberInfo() = LoginMemberInfo(
        no = no,
        email = email,
        nickname = nickname
    )
}
