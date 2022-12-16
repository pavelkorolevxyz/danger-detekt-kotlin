package xyz.pavelkorolev.danger.detekt.model

data class DetektViolation(
    val filePath: String? = null,
    val message: String? = null,
    val ruleId: String? = null,
    val location: DetektViolationLocation? = null,
    val severity: DetektViolationSeverity? = null,
)
