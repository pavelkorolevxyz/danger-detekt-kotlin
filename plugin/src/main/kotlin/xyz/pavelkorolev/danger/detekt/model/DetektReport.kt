package xyz.pavelkorolev.danger.detekt.model

data class DetektReport(
    val violations: List<DetektViolation> = emptyList(),
) {

    /**
     * Returns total violations count
     */
    val count: Int get() = violations.size

    /**
     * Returns true if there are no violations found
     */
    val isEmpty: Boolean get() = violations.isEmpty()
}
