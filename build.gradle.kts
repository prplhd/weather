plugins {
    id("java")
    id("war")
}

group = "ru.prplhd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(platform("org.springframework:spring-framework-bom:7.0.7"))
    implementation(platform("org.springframework.data:spring-data-bom:2025.1.5"))

    implementation("org.springframework:spring-webmvc")
    implementation("org.springframework:spring-orm")
    implementation("org.springframework.data:spring-data-jpa")

    implementation("org.thymeleaf:thymeleaf-spring6:3.1.5.RELEASE")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    compileOnly("jakarta.annotation:jakarta.annotation-api:3.0.0")
    implementation("org.projectlombok:lombok:1.18.46")

    implementation("com.zaxxer:HikariCP:7.0.2")
    runtimeOnly("org.postgresql:postgresql:42.7.11")
    implementation("org.liquibase:liquibase-core:5.0.2")
    implementation("org.hibernate.orm:hibernate-core:7.3.2.Final")
}

tasks.test {
    useJUnitPlatform()
}