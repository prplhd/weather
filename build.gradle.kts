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
    implementation("org.springframework:spring-webmvc")

    implementation("org.thymeleaf:thymeleaf-spring6:3.1.5.RELEASE")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
}

tasks.test {
    useJUnitPlatform()
}