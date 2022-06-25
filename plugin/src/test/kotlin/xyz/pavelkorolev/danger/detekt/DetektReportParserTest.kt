package xyz.pavelkorolev.danger.detekt

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.model.DetektError
import xyz.pavelkorolev.danger.detekt.model.DetektErrorSeverity
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektReportFile
import xyz.pavelkorolev.danger.detekt.utils.TestData

internal class DetektReportParserTest : FunSpec(
    {
        val parser = DetektReportParser()
        test("should parse empty file list") {
            val report = parser.parse(listOf())
            report.isEmpty shouldBe true
        }
        test("should parse file with single report") {
            val report = parser.parse(listOf(TestData.singleReportFile))
            report shouldBe DetektReport(
                files = listOf(
                    DetektReportFile(
                        name = "A.kt",
                        errors = listOf(
                            DetektError(
                                column = 1,
                                line = 1,
                                message = "Error A",
                                severity = DetektErrorSeverity.ERROR,
                                source = "Rule A",
                            ),
                        ),
                    ),
                ),
            )
        }
        test("should merge multiple files reports") {
            val report = parser.parse(
                listOf(
                    TestData.singleReportFile,
                    TestData.multipleReportFile,
                ),
            )
            report shouldBe DetektReport(
                files = listOf(
                    DetektReportFile(
                        name = "A.kt",
                        errors = listOf(
                            DetektError(
                                column = 1,
                                line = 1,
                                message = "Error A",
                                severity = DetektErrorSeverity.ERROR,
                                source = "Rule A",
                            ),
                        ),
                    ),
                    DetektReportFile(
                        name = "B.kt",
                        errors = listOf(
                            DetektError(
                                column = 2,
                                line = 3,
                                message = "Info B",
                                severity = DetektErrorSeverity.INFO,
                                source = "Rule B",
                            ),
                            DetektError(
                                column = 4,
                                line = 5,
                                message = "Info B2",
                                severity = DetektErrorSeverity.INFO,
                                source = "Rule B2",
                            ),
                            DetektError(
                                column = 6,
                                line = 7,
                                message = "Info B3",
                                severity = DetektErrorSeverity.INFO,
                                source = "Rule B3",
                            ),
                        ),
                    ),
                    DetektReportFile(
                        name = "C.kt",
                        errors = listOf(
                            DetektError(
                                column = 8,
                                line = 9,
                                message = "Warning C",
                                severity = DetektErrorSeverity.WARNING,
                                source = "Rule C",
                            ),
                        ),
                    ),
                ),
            )
        }
    },
)
