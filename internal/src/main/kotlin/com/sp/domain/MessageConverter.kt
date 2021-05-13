package com.sp.domain

import org.springframework.context.*
import org.springframework.context.i18n.*
import org.springframework.context.support.*

/**
 * @author Jaedoo Lee
 */
object MessageConverter {

    private val message: MessageSource = ResourceBundleMessageSource().apply {
        setBasenames("messages/error")
        setDefaultEncoding("UTF-8")
    }

    fun getMessage(code: String, vararg args: Any): String {
        val default = "No message for $code"
        return message.getMessage(code, args, default, LocaleContextHolder.getLocale()) ?: default
    }

}
