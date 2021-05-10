package com.sp.domain

/**
 * @author Jaedoo Lee
 */
enum class AuthErrorCode(override val simpleCode: String) : ErrorCode {

    TOKEN_EXPIRED("A001"),
    INVALID_TOKEN("A002")
    ;

    override val code: String
        get() = "error.auth.$simpleCode"
}
