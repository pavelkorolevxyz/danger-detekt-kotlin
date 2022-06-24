plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "xyz.pavelkorolev"
version = "0.1.0"

dependencies {
    implementation("systems.danger:danger-kotlin-sdk:1.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
