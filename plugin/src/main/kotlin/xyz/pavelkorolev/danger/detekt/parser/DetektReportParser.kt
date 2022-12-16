package xyz.pavelkorolev.danger.detekt.parser

import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.CheckstyleReportParser
import xyz.pavelkorolev.danger.detekt.parser.sarif.SarifReportParser
import java.io.File

/**
 * Class which encapsulate detekt reports deserialization logic
 */
internal class DetektReportParser {

    private val checkstyleParser by lazy { CheckstyleReportParser() }
    private val sarifParser by lazy { SarifReportParser() }

    /**
     * Parses all given report files and returns [DetektReport].
     * Could be used with XML and SARIF files.
     * All violations from all files will be merged into one final report.
     */
    fun parse(files: List<File>): DetektReport {
        val violations = files
            .map(::parse)
            .flatMap { it.violations }
        return DetektReport(violations)
    }

    private fun parse(file: File): DetektReport = when (file.extension) {
        "xml" -> checkstyleParser.parse(file)
        else -> sarifParser.parse(file)
    }
}
