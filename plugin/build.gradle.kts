plugins {
    kotlin("jvm")
    id("convention.detekt")
    id("convention.publishing")
    id("convention.testing")
}

group = "xyz.pavelkorolev.danger.detekt"
version = "1.0.0"

dependencies {
    implementation("systems.danger:danger-kotlin-sdk:1.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")

    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}
