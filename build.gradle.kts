import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.3"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.example.kotlin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17


repositories {
	mavenCentral()
}

/**
 * implementation("org.springframework.boot:spring-boot-starter-webflux")
 * 		- spring webflux(비동기, non-block)
 *
 * implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
 * 		- spring webflux를 DB에 연결하기 위해 r2dbc 사용
 * 		- 기존 JDBC는 Blocking 방식으로 동작하는 문제가 있어
 * 			Reactive Relational Database Connectivity(R2DBC)를 이용해야 완전한 비동기, NON-Blocking 어플리케이션 개발 가능
 *
 * runtimeOnly("org.mariadb:r2dbc-mariadb:1.1.3")
 * 		- r2dbc mariadb 사용
 * 		- https://mariadb.com/resources/blog/unblock-your-applications-with-r2dbc-spring-data-and-mariadb/
 * 	
 * implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
 * implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
 * implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
 * 		- 코루틴 사용하기 위해 추가
 */
dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	runtimeOnly("org.mariadb:r2dbc-mariadb:1.1.3")

	implementation("org.springframework.boot:spring-boot-starter-rsocket")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.github.javafaker:javafaker:1.0.2")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.jetbrains:markdown:0.2.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}
