package com.sp.presentation.router

import com.sp.presentation.handler.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.reactive.function.server.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class TokenRouter(
    private val authHandler: AuthHandler
) {
    @Bean
    fun routeToken(): RouterFunction<ServerResponse> {
        return coRouter {
            ("/internal/auth" and headers { "1.0" in it.header("Version") }).nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    POST("token", authHandler::createToken)
                }
            }
        }
    }
}
