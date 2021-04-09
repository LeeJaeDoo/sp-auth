val spring_security_oauth2_version: String by project
val jwt_version: String by project

dependencies {

    // oAuth2.0
    /** @see https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Features-Matrix */
    implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure") {
        exclude("org.springframework.security.oauth", "spring-security-oauth2")
    }

    implementation("com.auth0:java-jwt:3.11.0")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}
