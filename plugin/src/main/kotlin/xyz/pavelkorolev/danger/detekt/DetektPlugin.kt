@file:Suppress("MemberVisibilityCanBePrivate")

package xyz.pavelkorolev.danger.detekt

import systems.danger.kotlin.sdk.DangerPlugin
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.parser.DetektReportParser
import java.io.File

/**
 * Detekt artifacts parse and report plugin to use in Dangerfiles
 *
 * Usage:
 * ```
 * register.plugin(DetektPlugin)
 * val report = DetektPlugin.parse(files)
 * DetektPlugin.report(report)
 * ```
 * or
 * ```
 * DetektPlugin.parseAndReport(files)
 * ```
 */
object DetektPlugin : DangerPlugin() {

    override val id: String = "detekt-plugin"

    private val parser = DetektReportParser()

    /**
     * Parses and reports violations from given report files using given [DetektViolationReporter]
     * Could be used with XML and SARIF files.
     */
    fun parseAndReport(
        vararg files: File,
        reporter: DetektViolationReporter = DefaultDetektViolationReporter(context),
    ) {
        val report = parse(*files)
        report(report, reporter)
    }

    /**
     * Parses detekt report files and return deserialized report object [DetektReport]
     * Could be used with XML and SARIF files.
     */
    fun parse(
        vararg files: File,
    ): DetektReport = parser.parse(files.asList())

    /**
     * Reports violations from [DetektReport] with given [DetektViolationReporter]
     */
    fun report(
        report: DetektReport,
        reporter: DetektViolationReporter = DefaultDetektViolationReporter(context),
    ) {
        for (violation in report.violations) {
            reporter.report(violation)
        }
    }
}
