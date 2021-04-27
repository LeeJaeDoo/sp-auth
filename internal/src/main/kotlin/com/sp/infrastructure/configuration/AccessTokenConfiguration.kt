package com.sp.infrastructure.configuration

import com.sp.domain.*
import com.sp.infrastructure.token.*
import org.springframework.context.annotation.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class AccessTokenConfiguration {

    @Bean
    fun adminTokenService(): TokenService {
        return JwtTokenService()
    }

}
