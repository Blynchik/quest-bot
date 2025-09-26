plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.blynchik"
version = "0.0.1-SNAPSHOT"
description = "Telegram Quest Bot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.11.0")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.telegram:telegrambots-spring-boot-starter:6.9.7.1")
	implementation("org.telegram:telegrambotsextensions:6.9.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
