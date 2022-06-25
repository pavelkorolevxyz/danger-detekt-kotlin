package xyz.pavelkorolev.danger.detekt

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.fakes.FakeDangerContext
import xyz.pavelkorolev.danger.detekt.model.DetektError
import xyz.pavelkorolev.danger.detekt.model.DetektErrorSeverity
import java.io.File

internal class DefaultDetektErrorReporterTest : FunSpec(
    {
        lateinit var context: FakeDangerContext
        lateinit var reporter: DetektErrorReporter

        beforeTest {
            context = FakeDangerContext()
            reporter = DefaultDetektErrorReporter(context)
        }

        context("should report based on severity") {
            test("info") {
                val error = DetektError(severity = DetektErrorSeverity.INFO)
                reporter.report(error, null)
                context.messages shouldHaveSize 1
                context.violationsCount shouldBe 1
            }
            test("warning") {
                val error = DetektError(severity = DetektErrorSeverity.WARNING)
                reporter.report(error, null)
                context.warnings shouldHaveSize 1
                context.violationsCount shouldBe 1
            }
            test("error") {
                val error = DetektError(severity = DetektErrorSeverity.ERROR)
                reporter.report(error, null)
                context.fails shouldHaveSize 1
                context.violationsCount shouldBe 1
            }
        }

        context("should report with correct params") {
            val error = DetektError(
                column = 1,
                line = 2,
                message = "Test",
                severity = DetektErrorSeverity.WARNING,
                source = "detekt.Test",
            )
            val fileName = "Test.kt"
            reporter.report(error, fileName)

            val violation = context.warnings.first()
            test("message") {
                violation.message shouldBe "Detekt: Test, Rule: detekt.Test"
            }
            test("file") {
                violation.file shouldBe fileName
            }
            test("line") {
                violation.line shouldBe 2
            }
        }

        context("should report with message if params are null") {
            test("file") {
                val error = DetektError(
                    line = 2,
                    severity = DetektErrorSeverity.WARNING,
                )
                reporter.report(error, fileName = null)
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().file.shouldBeNull()
            }
            test("line") {
                val error = DetektError(
                    line = null,
                    severity = DetektErrorSeverity.WARNING,
                )
                reporter.report(error, fileName = "report.xml")
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().line.shouldBeNull()
            }
            test("message") {
                val error = DetektError(
                    message = "Message",
                    severity = DetektErrorSeverity.WARNING,
                )
                reporter.report(error, fileName = "report.xml")
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().message shouldBe "Detekt: Message"
            }
            test("source") {
                val error = DetektError(
                    source = "Source",
                    severity = DetektErrorSeverity.WARNING,
                )
                reporter.report(error, fileName = "report.xml")
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().message shouldBe "Rule: Source"
            }
        }

        test("should report relative path") {
            val targetFile = File("report.xml")
            val error = DetektError(line = 1, severity = DetektErrorSeverity.WARNING)
            reporter.report(error, targetFile.absolutePath)
            context.warnings.first().file shouldBe "report.xml"
        }
    },
)
