package xyz.pavelkorolev.danger.detekt.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationLocation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import xyz.pavelkorolev.danger.detekt.utils.TestData

internal class DetektReportParserTest : FunSpec(
    {
        val parser = DetektReportParser()
        test("should parse empty file list") {
            val report = parser.parse(listOf())
            report.isEmpty shouldBe true
        }
        test("should parse file with single report") {
            val report = parser.parse(listOf(TestData.CheckstyleReport.single))
            report shouldBe DetektReport(
                violations = listOf(
                    DetektViolation(
                        filePath = "A.kt",
                        message = "Error A",
                        severity = DetektViolationSeverity.ERROR,
                        ruleId = "Rule A",
                        location = DetektViolationLocation(
                            startLine = 1,
                            startColumn = 1,
                        ),
                    ),
                ),
            )
        }
        test("should merge multiple files reports") {
            val report = parser.parse(
                listOf(
                    TestData.CheckstyleReport.single,
                    TestData.CheckstyleReport.multiple,
                ),
            )
            report shouldBe DetektReport(
                violations = listOf(
                    DetektViolation(
                        filePath = "A.kt",
                        message = "Error A",
                        severity = DetektViolationSeverity.ERROR,
                        ruleId = "Rule A",
                        location = DetektViolationLocation(
                            startLine = 1,
                            startColumn = 1,
                        ),
                    ),
                    DetektViolation(
                        filePath = "B.kt",
                        message = "Info B",
                        severity = DetektViolationSeverity.INFO,
                        ruleId = "Rule B",
                        location = DetektViolationLocation(
                            startLine = 3,
                            startColumn = 2,
                        ),
                    ),
                    DetektViolation(
                        filePath = "B.kt",
                        message = "Info B2",
                        severity = DetektViolationSeverity.INFO,
                        ruleId = "Rule B2",
                        location = DetektViolationLocation(
                            startLine = 5,
                            startColumn = 4,
                        ),
                    ),
                    DetektViolation(
                        filePath = "B.kt",
                        message = "Info B3",
                        severity = DetektViolationSeverity.INFO,
                        ruleId = "Rule B3",
                        location = DetektViolationLocation(
                            startLine = 7,
                            startColumn = 6,
                        ),
                    ),
                    DetektViolation(
                        filePath = "C.kt",
                        message = "Warning C",
                        severity = DetektViolationSeverity.WARNING,
                        ruleId = "Rule C",
                        location = DetektViolationLocation(
                            startLine = 9,
                            startColumn = 8,
                        ),
                    ),
                ),
            )
        }
        test("should parse checkstyle and sarif reports in one run") {
            val report = parser.parse(
                listOf(
                    TestData.CheckstyleReport.single,
                    TestData.SarifReport.multiple,
                ),
            )
            report shouldBe DetektReport(
                violations = listOf(
                    DetektViolation(
                        filePath = "A.kt",
                        message = "Error A",
                        ruleId = "Rule A",
                        location = DetektViolationLocation(
                            startLine = 1,
                            startColumn = 1,
                        ),
                        severity = DetektViolationSeverity.ERROR,
                    ),
                    DetektViolation(
                        filePath = "X.kt",
                        message = "Error X",
                        ruleId = "Rule X",
                        location = DetektViolationLocation(
                            endColumn = 1,
                            endLine = 20,
                            startColumn = 1,
                            startLine = 19,
                        ),
                        severity = DetektViolationSeverity.WARNING,
                    ),
                    DetektViolation(
                        filePath = "Y.kt",
                        message = "Error Y",
                        ruleId = "Rule Y",
                        location = DetektViolationLocation(
                            endColumn = 45,
                            endLine = 24,
                            startColumn = 44,
                            startLine = 24,
                        ),
                        severity = DetektViolationSeverity.ERROR,
                    ),
                ),
            )
        }
        test("should return correct count") {
            val report = parser.parse(
                listOf(
                    TestData.CheckstyleReport.single,
                    TestData.CheckstyleReport.multiple,
                ),
            )
            report.count shouldBe 5
            report.isEmpty.shouldBeFalse()
        }
        test("should report is empty correctly") {
            val report = parser.parse(
                listOf(
                    TestData.CheckstyleReport.empty,
                ),
            )
            report.count shouldBe 0
            report.isEmpty.shouldBeTrue()
        }
    },
)
