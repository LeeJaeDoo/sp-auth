package com.sp.domain

/**
 * @author Jaedoo Lee
 */
abstract class AbstractAuthInternalExceptions(
    errorCode: ErrorCode,
    vararg args: Any
) : AuthInternalException(errorCode, args)

class FrontTokenExpiredException : AbstractAuthInternalExceptions(AuthErrorCode.TOKEN_EXPIRED)
class InvalidFrontTokenException : AbstractAuthInternalExceptions(AuthErrorCode.INVALID_TOKEN)
