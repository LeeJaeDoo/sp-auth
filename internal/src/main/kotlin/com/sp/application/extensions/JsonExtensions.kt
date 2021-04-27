package com.sp.application.extensions

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.hibernate5.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.fasterxml.jackson.module.kotlin.*
import java.time.*
import java.time.format.*

/**
 * @author Jaedoo Lee
 */
val objectMapper: ObjectMapper = ObjectMapper().apply {
    registerKotlinModule()
    registerModule(Hibernate5Module())
    registerModule(JavaTimeModule().apply {
        addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) as JsonSerializer<LocalDateTime>?
        )
        addDeserializer(
            LocalDateTime::class.java,
            LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
    })
    enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}

fun Any.toJson(): String = objectMapper.writeValueAsString(this)
inline fun <reified T> String.toModel(): T = objectMapper.readValue(this, jacksonTypeRef<T>())
