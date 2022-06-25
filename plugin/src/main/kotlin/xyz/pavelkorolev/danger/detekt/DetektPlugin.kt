@file:Suppress("MemberVisibilityCanBePrivate")

package xyz.pavelkorolev.danger.detekt

import systems.danger.kotlin.sdk.DangerPlugin
import xyz.pavelkorolev.danger.detekt.model.DetektReport
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
     * Parses and reports violations from given report files using given [DetektErrorReporter]
     */
    fun parseAndReport(
        vararg files: File,
        reporter: DetektErrorReporter = DefaultDetektErrorReporter(context),
    ) {
        val report = parse(*files)
        report(report, reporter)
    }

    /**
     * Only parses detekt report files and return deserialized report object [DetektReport]
     */
    fun parse(
        vararg files: File,
    ): DetektReport = parser.parse(files.asList())

    /**
     * Reports violations from [DetektReport] with given [DetektErrorReporter]
     */
    fun report(
        report: DetektReport,
        reporter: DetektErrorReporter = DefaultDetektErrorReporter(context),
    ) {
        for (file in report.files) {
            for (error in file.errors) {
                reporter.report(
                    error = error,
                    fileName = file.name,
                )
            }
        }
    }
}
