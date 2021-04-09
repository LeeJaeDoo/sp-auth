import org.jetbrains.kotlin.gradle.tasks.*

val snippetsDir: String by extra("build/generated-snippets")
val springCloudVersion = property("spring_cloud_version") as String
val coroutineVersion = property("coroutines_version") as String
val querydslVersion = property("querydsl_version") as String

buildscript {
    repositories {
        repositories { mavenCentral() }
    }
}

plugins {
    val kotlinVersion = "1.4.30"
    val springBootVersion = "2.3.3.RELEASE"
    val springDependencyManagementVersion = "1.0.11.RELEASE"

    // PLUGIN: Language
    java

    // PLUGIN: Kotlin
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion apply false

    // PLUGIN: Spring Boot
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion
}

allprojects {
    repositories { mavenCentral() }
}

subprojects {
    group = "com.sp"
    version = "1.0"

    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    tasks {
        val action: KotlinCompile.() -> Unit = {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xuse-experimental=kotlin.Experimental")
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }

        compileKotlin(action)
        compileTestKotlin(action)

        configure<JavaPluginConvention> {
            sourceCompatibility = JavaVersion.VERSION_11
        }
    }

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

    dependencies {
        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))

        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude("junit", "junit")
        }

        // Spring Cloud
        implementation("org.springframework.cloud:spring-cloud-starter-config")     //  yml 파일명 bootstrap
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

        implementation("org.springframework.security:spring-security-core")
        implementation("org.jasypt:jasypt-springsecurity4:1.9.3")

        testImplementation("org.junit.platform:junit-platform-launcher")
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")

        // Query DSL
        implementation("com.querydsl:querydsl-jpa:$querydslVersion")
        implementation("com.querydsl:querydsl-sql:$querydslVersion") {
            exclude("joda-time")
        }
        kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")

        // JSON
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // coroutine
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutineVersion}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutineVersion}")

        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${coroutineVersion}")

        runtimeOnly("mysql:mysql-connector-java")

        dependencyManagement {
            imports {
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
            }
        }
    }

    tasks {
        test {
            useJUnitPlatform {
                excludeEngines = setOf("junit-vintage")
            }
            testLogging {
                showStandardStreams = false
            }
            outputs.dir(snippetsDir)
            jvmArgs = listOf("--illegal-access=permit")
        }
    }
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.17")
}
