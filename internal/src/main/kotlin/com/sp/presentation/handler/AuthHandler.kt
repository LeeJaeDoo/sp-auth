package com.sp.presentation.handler

import com.sp.application.*
import com.sp.application.model.*
import com.sp.presentation.request.*
import org.springframework.stereotype.*
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*

/**
 * @author Jaedoo Lee
 */
@Component
class AuthHandler(
    private val tokenCommandService: TokenCommandService
) {


    suspend fun getAuthentication(accessToken: String): String {
        return tokenCommandService.getAuthentication(accessToken)
    }

    suspend fun createToken(request: ServerRequest): ServerResponse {
        val memberNo = request.queryParams()["memberNo"]?.first().toString().toLong()
        val email = request.queryParams()["email"]?.first().toString()
        val nickname = request.queryParams()["nickname"]?.first().toString()
        return ok().bodyValueAndAwait(tokenCommandService.createToken(LoginRequest(memberNo, email, nickname)))
    }

}
