import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    // Used for mocking framework
    // https://kotlinlang.org/docs/all-open-plugin.html
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.31"
    // Use for hibernate framework
    // https://kotlinlang.org/docs/no-arg-plugin.html
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.31"
    // https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.31"

    id("com.palantir.docker") version "0.31.0"
    id("com.palantir.docker-run") version "0.31.0"

    kotlin("kapt") version "1.5.10"
}

group = "com.spacexdata.api"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    jcenter()
    mavenCentral()
}

// We have to add this because of the issue:
// "Getter methods of lazy classes cannot be final. Example: com.german.notificationappkotlin.domain.Client#getId"
// This is for every attribute, actually not only the ID of the Client
allOpen {
    annotation("javax.persistence.Entity")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("io.mockk:mockk:1.12.0")


    // Was not able to configure swagger
    // dependency complications and ui not working with certain spring boot versions
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
    // Using spring Open API!!
    implementation("org.springdoc:springdoc-openapi-ui:1.5.12")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.jetbrains.exposed:exposed-core:0.24.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.24.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.24.1")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    testImplementation("com.h2database:h2:2.0.202")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    implementation("com.amazonaws:aws-java-sdk-s3:1.12.130")

    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")

}

springBoot {
    mainClass.set("com.spacexdata.api.NotificationAppKotlinApplicationKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-Dspring.profiles.active=test")
}

docker {
    name = "${project.name}:${project.version}"
    copySpec.from("build").into("build")
    setDockerfile(file("Dockerfile"))
}

dockerRun {
    name = project.name
    image = "${project.name}:${project.version}"
    env(mapOf(Pair("SPRING_PROFILES_ACTIVE", "prod")))
    ports("8080:8080")
}