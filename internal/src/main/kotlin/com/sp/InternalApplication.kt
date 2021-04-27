package com.sp

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.properties.*
import org.springframework.cloud.netflix.eureka.*
import org.springframework.web.reactive.config.*

/**
 * @author Jaedoo Lee
 */
@SpringBootApplication
@EnableEurekaClient
@ConfigurationPropertiesScan
@EnableWebFlux
class InternalApplication

fun main(args: Array<String>) {
    runApplication<InternalApplication>(*args)
}
