plugins {
    base
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}
