package xyz.pavelkorolev.danger.detekt

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.fakes.FakeDangerContext
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationLocation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import java.io.File

internal class DefaultDetektViolationReporterTest : FunSpec(
    {
        lateinit var context: FakeDangerContext
        lateinit var reporter: DetektViolationReporter

        beforeTest {
            context = FakeDangerContext()
            reporter = DefaultDetektViolationReporter(context)
        }

        context("should report based on severity") {
            test("info") {
                val violation = DetektViolation(severity = DetektViolationSeverity.INFO)
                reporter.report(violation)
                context.messages shouldHaveSize 1
                context.violationsCount shouldBe 1
            }
            test("warning") {
                val violation = DetektViolation(severity = DetektViolationSeverity.WARNING)
                reporter.report(violation)
                context.warnings shouldHaveSize 1
                context.violationsCount shouldBe 1
            }
            test("error") {
                val violation = DetektViolation(severity = DetektViolationSeverity.ERROR)
                reporter.report(violation)
                context.fails shouldHaveSize 1
                context.violationsCount shouldBe 1
            }
        }

        context("should report with correct params") {
            val filePath = "Test.kt"
            val violation = DetektViolation(
                filePath = filePath,
                message = "Test",
                severity = DetektViolationSeverity.WARNING,
                ruleId = "detekt.Test",
                location = DetektViolationLocation(
                    startLine = 2,
                    startColumn = 1,
                ),
            )
            reporter.report(violation)

            val firstWarning = context.warnings.first()
            test("message") {
                firstWarning.message shouldBe "\n**Detekt**: Test\n**Rule**: detekt.Test"
            }
            test("file") {
                firstWarning.file shouldBe filePath
            }
            test("line") {
                firstWarning.line shouldBe 2
            }
        }

        context("should not report file and line if inline option disabled") {
            val filePath = "Test.kt"
            val violation = DetektViolation(
                filePath = filePath,
                message = "Test",
                severity = DetektViolationSeverity.WARNING,
                ruleId = "detekt.Test",
                location = DetektViolationLocation(
                    startLine = 1,
                    startColumn = 2,
                ),
            )
            val noInlineReporter = DefaultDetektViolationReporter(context, isInlineEnabled = false)
            noInlineReporter.report(violation)

            val firstWarning = context.warnings.first()
            test("message") {
                firstWarning.message shouldBe "\n**Detekt**: Test\n**Rule**: detekt.Test"
            }
            test("file") {
                firstWarning.file.shouldBeNull()
            }
            test("line") {
                firstWarning.line.shouldBeNull()
            }
        }

        context("should report with message if params are null") {
            test("file") {
                val violation = DetektViolation(
                    severity = DetektViolationSeverity.WARNING,
                    location = DetektViolationLocation(
                        startLine = 2,
                        startColumn = 3,
                    ),
                )
                reporter.report(violation)
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().file.shouldBeNull()
            }
            test("location") {
                val violation = DetektViolation(
                    severity = DetektViolationSeverity.WARNING,
                    location = null,
                )
                reporter.report(violation)
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().line.shouldBeNull()
            }
            test("message") {
                val violation = DetektViolation(
                    message = "Message",
                    severity = DetektViolationSeverity.WARNING,
                )
                reporter.report(violation)
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().message shouldBe "\n**Detekt**: Message"
            }
            test("source") {
                val violation = DetektViolation(
                    ruleId = "Source",
                    severity = DetektViolationSeverity.WARNING,
                )
                reporter.report(violation)
                context.warnings.shouldNotBeEmpty()
                context.warnings.first().message shouldBe "\n**Rule**: Source"
            }
        }

        test("should report relative path") {
            val targetFile = File("report.xml")
            val violation = DetektViolation(
                filePath = targetFile.absolutePath,
                severity = DetektViolationSeverity.WARNING,
                location = DetektViolationLocation(
                    startLine = 1,
                )
            )
            reporter.report(violation)
            context.warnings.first().file shouldBe "report.xml"
        }
    },
)
