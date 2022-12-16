package xyz.pavelkorolev.danger.detekt.parser.sarif

import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationLocation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import xyz.pavelkorolev.danger.detekt.parser.sarif.model.SarifRoot
import xyz.pavelkorolev.danger.detekt.parser.sarif.model.SarifRunResult
import xyz.pavelkorolev.danger.detekt.parser.sarif.model.SarifRunResultSeverity

internal class SarifReportMapper {

    fun map(report: SarifRoot): DetektReport {
        val violations = mutableListOf<DetektViolation>()
        for (run in report.runs) {
            for (result in run.results) {
                violations += createViolation(result)
            }
        }
        return DetektReport(violations)
    }

    private fun createViolation(
        result: SarifRunResult,
    ): DetektViolation {
        val physicalLocation = result.locations.firstOrNull()?.physicalLocation
        return DetektViolation(
            filePath = physicalLocation?.artifactLocation?.uri,
            message = result.message?.text,
            ruleId = result.ruleId,
            location = physicalLocation?.region?.let { region ->
                DetektViolationLocation(
                    startLine = region.startLine,
                    startColumn = region.startColumn,
                    endLine = region.endLine,
                    endColumn = region.endColumn,
                )
            },
            severity = when (result.level) {
                SarifRunResultSeverity.INFO -> DetektViolationSeverity.INFO
                SarifRunResultSeverity.WARNING -> DetektViolationSeverity.WARNING
                SarifRunResultSeverity.ERROR -> DetektViolationSeverity.ERROR
                null -> null
            },
        )
    }
}
