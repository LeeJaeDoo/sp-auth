package com.sp.presentation.controller

import com.sp.application.model.*
import com.sp.presentation.handler.*
import org.springframework.http.*
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

/**
 * @author Jaedoo Lee
 */
@RestController
@RequestMapping(path = ["/internal/auth"])
class AuthRestController(
    private val authHandler: AuthHandler
) {
    @GetMapping(
        path = ["token/check"],
        headers = ["Version=1.0"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun checkToken(@RequestHeader accessToken: String?): ResponseEntity<String> {
        if (accessToken.isNullOrBlank()) throw Exception()
        return ok(authHandler.getAuthentication(accessToken))
    }
}
