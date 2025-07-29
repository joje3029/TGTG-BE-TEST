plugins {
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.23"
	kotlin("kapt") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

group = "com.tgtg"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	 kapt("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// JWT 관련 의존성
	//implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	//runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	//runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named("check") {
    dependsOn(tasks.named("ktlintCheck"))// ktlint 스타일 검사도 포함
    dependsOn(tasks.named("detekt")) // detekt 정적 분석도 포함
}


ktlint {
	//버전set을 없애서 plugin에 맞는 기본 set이 되게 함.
    verbose.set(true)// 콘솔에 어떤 룰을 위반했는지 자세히 출력
	ignoreFailures.set(false)  //  CI 연동 시 일반적으로 false로 설정해 코드 스타일 위반 시 빌드 실패하도록 함
	android.set(false) // 우리는 android 프로젝트가  아니니까 false
    outputToConsole.set(true)// 결과를 콘솔에 출력 (IDE 없이도 확인 가능)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

detekt {
    config.setFrom(files("config/detekt/detekt.yml"))  // Detekt 룰 설정 파일 위치. 직접 생성하거나 detektGenerateConfig로 생성 가능
    buildUponDefaultConfig = true // 기본 설정을 기반으로 커스텀 설정을 오버라이드 (기본값 보존)
	allRules = false // 비활성화된 규칙 포함 여부 (true면 모든 룰 활성화 → 매우 엄격해짐)
	parallel = true // 파일을 병렬로 분석하여 속도 향상
	baseline = file("config/detekt/detekt-baseline.xml") // 기존 위반을 무시하고 신규 위반만 체크 (선택사항)
}


