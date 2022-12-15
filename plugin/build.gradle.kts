plugins {
    kotlin("jvm")
    id("convention.detekt")
    id("convention.publishing")
    id("convention.testing")
}

group = "xyz.pavelkorolev.danger.detekt"
version = "1.1.0"

dependencies {
    implementation("systems.danger:danger-kotlin-sdk:1.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.0")

    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
}
