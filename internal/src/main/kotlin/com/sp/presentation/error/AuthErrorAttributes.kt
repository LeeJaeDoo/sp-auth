package com.sp.presentation.error

import com.sp.domain.*
import org.springframework.beans.*
import org.springframework.boot.web.reactive.error.*
import org.springframework.core.codec.*
import org.springframework.http.*
import org.springframework.stereotype.*
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.*
import java.time.*

/**
 * @author Jaedoo Lee
 */
@Component
class AuthErrorAttributes : ErrorAttributes {
    override fun getError(request: ServerRequest): Throwable {
        return request.attributeOrNull("AUTH") as Throwable?
            ?: IllegalStateException("Missing exception attribute in ServerWebExchange")
    }

    override fun getErrorAttributes(request: ServerRequest, includeStackTrace: Boolean): Map<String, Any> {
        val attributes = linkedMapOf<String, Any>()
        attributes["timestamp"] = LocalDateTime.now()
        attributes["path"] = "${request.methodName()} ${request.path()}"
        val error = getError(request)
        val httpStatus = determineHttpStatus(error)
        attributes["status"] = httpStatus.value()
        attributes["error"] = httpStatus.reasonPhrase
        val internalApi = request.path().startsWith("/internal")
        attributes["code"] = determineCode(error)
        attributes["message"] = determineMessage(error, internalApi)

        return attributes
    }

    override fun storeErrorInformation(error: Throwable?, exchange: ServerWebExchange) {
        exchange.attributes.putIfAbsent("AUTH", error)
    }

    private fun determineHttpStatus(error: Throwable): HttpStatus = when (error) {
        is ResponseStatusException -> error.status
        is TypeMismatchException, is DecodingException, is NumberFormatException -> HttpStatus.BAD_REQUEST
        is AuthInternalException -> HttpStatus.UNAUTHORIZED
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    private fun determineCode(error: Throwable): String = when (error) {
        is TypeMismatchException, is DecodingException, is NumberFormatException -> CommonErrorCode.REQUEST_ERROR.simpleCode
        is AuthInternalException -> error.errorCode.simpleCode
        else -> "99999"
    }

    private fun determineMessage(error: Throwable, internalApi: Boolean): String = when (error) {
        is ResponseStatusException -> error.cause?.message ?: error.reason
        is TypeMismatchException, is DecodingException, is NumberFormatException ->
            MessageConverter.getMessage(CommonErrorCode.REQUEST_ERROR.code)
        is AuthInternalException -> error.message
        else -> if (internalApi) "${error.message}" else "Server error"
    }
}
