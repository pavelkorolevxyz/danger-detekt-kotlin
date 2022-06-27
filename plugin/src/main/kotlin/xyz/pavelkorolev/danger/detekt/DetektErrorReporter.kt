package xyz.pavelkorolev.danger.detekt

import systems.danger.kotlin.sdk.DangerContext
import xyz.pavelkorolev.danger.detekt.model.DetektError
import xyz.pavelkorolev.danger.detekt.model.DetektErrorSeverity
import java.io.File

/**
 * Interface of violation reporting logic.
 *
 * If you wish to report only error level violations or write custom message with emojis
 * feel free to implement this.
 *
 * You could use DangerContext to call [DangerContext.message], [DangerContext.warn],
 * [DangerContext.fail] etc functions in your reporter.
 */
fun interface DetektErrorReporter {

    /**
     * Reports a violation [error] in file with given [fileName] found by detekt.
     */
    fun report(
        error: DetektError,
        fileName: String?,
    )
}

/**
 * Out of box reporter implementation which sends [DangerContext.message], [DangerContext.warn] and
 * [DangerContext.fail] based on violation severity with default message, file and line if provided.
 * Created message looks like "**Detekt:** Something wrong **Rule:** detekt.SomethingWrongRule"
 */
class DefaultDetektErrorReporter(
    private val context: DangerContext,
) : DetektErrorReporter {

    private val pathPrefix = File("").absolutePath

    override fun report(
        error: DetektError,
        fileName: String?,
    ) {
        val message = createMessage(error)
        val severity = error.severity ?: DetektErrorSeverity.WARNING
        val file = fileName?.let(::File)
        val filePath = file?.let(::createFilePath)
        val line = error.line

        if (line == null || filePath == null) {
            report(message, severity)
            return
        }
        report(message, severity, filePath, line)
    }

    private fun report(
        message: String,
        severity: DetektErrorSeverity,
    ) {
        when (severity) {
            DetektErrorSeverity.INFO -> context.message(message)
            DetektErrorSeverity.WARNING -> context.warn(message)
            DetektErrorSeverity.ERROR -> context.fail(message)
        }
    }

    private fun report(
        message: String,
        severity: DetektErrorSeverity,
        filePath: String,
        line: Int,
    ) {
        when (severity) {
            DetektErrorSeverity.INFO -> context.message(message, filePath, line)
            DetektErrorSeverity.WARNING -> context.warn(message, filePath, line)
            DetektErrorSeverity.ERROR -> context.fail(message, filePath, line)
        }
    }

    private fun createFilePath(file: File): String? {
        if (file.absolutePath == pathPrefix) return null
        return file.absolutePath
            .removePrefix(pathPrefix)
            .removePrefix(File.separator)
    }

    private fun createMessage(error: DetektError): String {
        val message = error.message?.let { "**Detekt**: $it" }
        val rule = error.source?.let { "**Rule**: $it" }
        return listOfNotNull(
            "", // start message with blank line
            message,
            rule,
        ).joinToString(separator = "\n")
    }
}
