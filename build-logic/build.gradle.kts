plugins {
    base
    id("io.gitlab.arturbosch.detekt").version("1.20.0")
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    }
}
