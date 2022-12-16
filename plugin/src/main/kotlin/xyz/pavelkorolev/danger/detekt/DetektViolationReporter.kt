package xyz.pavelkorolev.danger.detekt

import systems.danger.kotlin.sdk.DangerContext
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import java.io.File

/**
 * Interface of violation reporting logic.
 *
 * If you want to report only error level violations or write custom message with emojis
 * feel free to implement this.
 *
 * You could use [DangerContext] to call [DangerContext.message], [DangerContext.warn],
 * [DangerContext.fail] etc functions in your reporter.
 */
fun interface DetektViolationReporter {

    /**
     * Reports a [violation] found by detekt.
     */
    fun report(violation: DetektViolation)
}

/**
 * Out of box reporter implementation which sends [DangerContext.message], [DangerContext.warn] and
 * [DangerContext.fail] based on violation severity with default message, file and line if provided.
 *
 * Created message looks like "**Detekt:** Something wrong **Rule:** detekt.SomethingWrongRule"
 */
class DefaultDetektViolationReporter(
    private val context: DangerContext,
    private val isInlineEnabled: Boolean = true,
) : DetektViolationReporter {

    private val pathPrefix = File("").absolutePath

    override fun report(
        violation: DetektViolation,
    ) {
        val message = createMessage(violation)
        val severity = violation.severity ?: DetektViolationSeverity.WARNING
        val file = violation.filePath?.let(::File)
        val filePath = file?.let(::createFilePath)
        val line = violation.location?.startLine

        if (!isInlineEnabled || line == null || filePath == null) {
            report(message, severity)
            return
        }
        report(message, severity, filePath, line)
    }

    private fun report(
        message: String,
        severity: DetektViolationSeverity,
    ) {
        when (severity) {
            DetektViolationSeverity.INFO -> context.message(message)
            DetektViolationSeverity.WARNING -> context.warn(message)
            DetektViolationSeverity.ERROR -> context.fail(message)
        }
    }

    private fun report(
        message: String,
        severity: DetektViolationSeverity,
        filePath: String,
        line: Int,
    ) {
        when (severity) {
            DetektViolationSeverity.INFO -> context.message(message, filePath, line)
            DetektViolationSeverity.WARNING -> context.warn(message, filePath, line)
            DetektViolationSeverity.ERROR -> context.fail(message, filePath, line)
        }
    }

    private fun createFilePath(file: File): String? {
        if (file.absolutePath == pathPrefix) return null
        return file.absolutePath.removePrefix(pathPrefix + File.separator)
    }

    private fun createMessage(violation: DetektViolation): String {
        val message = violation.message?.let { "**Detekt**: $it" }
        val rule = violation.ruleId?.let { "**Rule**: $it" }
        return listOfNotNull(
            "", // start message with blank line
            message,
            rule,
        ).joinToString(separator = "\n")
    }
}
