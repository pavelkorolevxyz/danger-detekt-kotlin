package xyz.pavelkorolev.danger.detekt

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.fakes.FakeDangerContext
import xyz.pavelkorolev.danger.detekt.fakes.FakeReporter
import xyz.pavelkorolev.danger.detekt.model.DetektError
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektReportFile
import xyz.pavelkorolev.danger.detekt.utils.TestData

internal class DetektPluginTest : FunSpec(
    {
        lateinit var context: FakeDangerContext
        val plugin = DetektPlugin

        beforeTest {
            context = FakeDangerContext()
            plugin.context = context
        }

        test("should return no violations on empty report") {
            plugin.parseAndReport(TestData.emptyReportFile)
            context.violationsCount shouldBe 0
        }

        test("should return one warning violation for single error report") {
            plugin.parseAndReport(TestData.singleReportFile)
            context.violationsCount shouldBe 1
        }

        test("should return multiple warning violations for larger reports") {
            plugin.parseAndReport(TestData.multipleReportFile)
            context.violationsCount shouldBe 4
        }

        test("should merge multiple reports") {
            plugin.parseAndReport(TestData.singleReportFile, TestData.multipleReportFile)
            context.violationsCount shouldBe 5
        }

        test("should report based on severity") {
            plugin.parseAndReport(TestData.singleReportFile, TestData.multipleReportFile)
            context.warnings.shouldNotBeEmpty()
            context.messages.shouldNotBeEmpty()
            context.fails.shouldNotBeEmpty()
        }

        test("should use custom reporter if provided") {
            val reporter = FakeReporter()
            val report = DetektReport(
                files = listOf(
                    DetektReportFile(errors = listOf(DetektError()))
                )
            )
            plugin.report(report, reporter)
            reporter.outputs shouldHaveSize 1
            println(reporter.outputs)
        }

        test("should not crash with malformed report") {
            shouldNotThrow<Throwable> {
                plugin.parseAndReport(TestData.malformedReportFile)
                context.fails
            }
        }
    },
)

