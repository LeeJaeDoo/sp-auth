package com.sp.presentation.controller

import com.epages.restdocs.apispec.*
import com.ninjasquad.springmockk.*
import com.sp.application.*
import com.sp.presentation.handler.*
import com.sp.presentation.router.*
import io.mockk.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.*
import org.springframework.boot.test.autoconfigure.web.reactive.*
import org.springframework.context.*
import org.springframework.http.*
import org.springframework.restdocs.*
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.webtestclient.*
import org.springframework.test.context.*
import org.springframework.test.web.reactive.server.*

/**
 * @author Jaedoo Lee
 */
@WebFluxTest
@ExtendWith(RestDocumentationExtension::class)
@ContextConfiguration(classes = [AuthRestController::class])
internal class AuthRestControllerTest(private val context: ApplicationContext) {

    private lateinit var webTestClient: WebTestClient

    private val TAG = "AUTH"

    @MockkBean
    private lateinit var authHandler: AuthHandler

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
            .configureClient()
            .baseUrl("http://localhost:8083")
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun `토큰 검증`() {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJubyI6MSwibmlja25hbWUiOiJlbmVuIiwiaXNzIjoiU1AiLCJpYXQiOjE2MTc2MzYwNzcsImVtYWlsIjoiZGx3b2VuOUBuYXZlci5jb20ifQ.rrATAXpcrhAo6nG3KqZOu_IqFGr5NxUk_Hg9h3FJajk"
        val decodedToken = "{no\": 1,\"email\": \"dlwoen90@naver.com\",\"nickname\": \"두두\"}"

        coEvery { authHandler.getAuthentication(any()) } returns decodedToken

        webTestClient.get()
            .uri("/internal/auth/token/check")
            .header("Version", "1.0")
            .header("accessToken", token)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "authentication-token",
                    ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                            .tag(TAG)
                            .summary("회원 토큰 검증")
                            .description("회원 토큰 검증")
                            .requestHeaders(
                                ResourceDocumentation.headerWithName("Version")
                                    .description("버전"),
                                ResourceDocumentation.headerWithName("accessToken")
                                    .description(token)
                            ).build()
                    )
                )
            )
    }
}

