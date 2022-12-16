package xyz.pavelkorolev.danger.detekt.parser.checkstyle

import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationLocation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleError
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleErrorSeverity
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleRoot

internal class CheckstyleReportMapper {

    fun map(report: CheckstyleRoot): DetektReport {
        val violations = mutableListOf<DetektViolation>()
        for (file in report.files) {
            for (error in file.errors) {
                violations += createViolation(file.name, error)
            }
        }
        return DetektReport(violations)
    }

    private fun createViolation(
        filePath: String?,
        error: CheckstyleError,
    ): DetektViolation = DetektViolation(
        filePath = filePath,
        message = error.message,
        ruleId = error.source,
        location = DetektViolationLocation(
            startLine = error.line,
            startColumn = error.column,
        ),
        severity = when (error.severity) {
            CheckstyleErrorSeverity.INFO -> DetektViolationSeverity.INFO
            CheckstyleErrorSeverity.WARNING -> DetektViolationSeverity.WARNING
            CheckstyleErrorSeverity.ERROR -> DetektViolationSeverity.ERROR
            null -> null
        },
    )
}
