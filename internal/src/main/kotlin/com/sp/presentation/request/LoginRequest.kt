package com.sp.presentation.request

import com.sp.application.model.*

/**
 * @author Jaedoo Lee
 */
data class LoginRequest(
    val memberNo: Long,
    val email: String,
    val nickname: String
) {
    fun toMemberInfo() = LoginMemberInfo(
        no = memberNo,
        email = email,
        nickname = nickname
    )
}
