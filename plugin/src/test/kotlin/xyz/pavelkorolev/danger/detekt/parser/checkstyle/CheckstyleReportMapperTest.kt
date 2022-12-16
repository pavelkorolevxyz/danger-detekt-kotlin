package xyz.pavelkorolev.danger.detekt.parser.checkstyle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationLocation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleError
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleErrorSeverity
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleFile
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleRoot

class CheckstyleReportMapperTest : FunSpec(
    {
        val mapper = CheckstyleReportMapper()
        test("should map") {
            val checkstyleRoot = CheckstyleRoot(
                files = listOf(
                    CheckstyleFile(
                        name = "file1",
                        errors = listOf(
                            CheckstyleError(
                                line = 1,
                                column = 2,
                                severity = CheckstyleErrorSeverity.INFO,
                                message = "message1",
                                source = "source1",
                            ),
                            CheckstyleError(
                                line = 3,
                                column = 4,
                                severity = CheckstyleErrorSeverity.ERROR,
                                message = "message2",
                                source = "source2",
                            ),
                        ),
                    ),
                    CheckstyleFile(
                        name = "file2",
                        errors = listOf(
                            CheckstyleError(
                                line = 5,
                                column = 6,
                                severity = CheckstyleErrorSeverity.ERROR,
                                message = "message3",
                                source = "source3",
                            ),
                        ),
                    ),
                ),
            )
            val detektReport = mapper.map(checkstyleRoot)
            detektReport shouldBe DetektReport(
                violations = listOf(
                    DetektViolation(
                        filePath = "file1",
                        message = "message1",
                        ruleId = "source1",
                        location = DetektViolationLocation(
                            startLine = 1,
                            startColumn = 2,
                        ),
                        severity = DetektViolationSeverity.INFO,
                    ),
                    DetektViolation(
                        filePath = "file1",
                        message = "message2",
                        ruleId = "source2",
                        location = DetektViolationLocation(
                            startLine = 3,
                            startColumn = 4,
                        ),
                        severity = DetektViolationSeverity.ERROR,
                    ),
                    DetektViolation(
                        filePath = "file2",
                        message = "message3",
                        ruleId = "source3",
                        location = DetektViolationLocation(
                            startLine = 5,
                            startColumn = 6,
                        ),
                        severity = DetektViolationSeverity.ERROR,
                    ),
                ),
            )
        }
    },
)
