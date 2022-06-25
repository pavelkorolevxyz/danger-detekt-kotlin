plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.gitlab.arturbosch.detekt")
}

group = "xyz.pavelkorolev"
version = "0.1.0"

dependencies {
    implementation("systems.danger:danger-kotlin-sdk:1.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")

    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

detekt {
    buildUponDefaultConfig = true
    config = files("$rootDir/config/detekt/config.yml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(false)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
    }
}
