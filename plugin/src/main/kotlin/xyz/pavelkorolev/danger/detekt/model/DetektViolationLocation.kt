package xyz.pavelkorolev.danger.detekt.model

data class DetektViolationLocation(
    val startLine: Int? = null,
    val startColumn: Int? = null,
    val endLine: Int? = null,
    val endColumn: Int? = null,
)
