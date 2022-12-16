package xyz.pavelkorolev.danger.detekt.parser.sarif

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.model.DetektViolation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationLocation
import xyz.pavelkorolev.danger.detekt.model.DetektViolationSeverity
import xyz.pavelkorolev.danger.detekt.parser.sarif.model.*

class SarifReportMapperTest : FunSpec(
    {
        val mapper = SarifReportMapper()
        test("should map") {
            val sarifRoot = SarifRoot(
                runs = listOf(
                    SarifRun(
                        results = listOf(
                            SarifRunResult(
                                ruleId = "ruleId",
                                message = SarifRunResultMessage(
                                    text = "message",
                                ),
                                level = SarifRunResultSeverity.WARNING,
                                locations = listOf(
                                    SarifRunResultLocation(
                                        physicalLocation = SarifRunResultPhysicalLocation(
                                            artifactLocation = SarifRunResultArtifactLocation(
                                                uri = "file1",
                                            ),
                                            region = SarifRunResultLocationRegion(
                                                startLine = 1,
                                                startColumn = 2,
                                                endLine = 3,
                                                endColumn = 4,
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                            SarifRunResult(
                                ruleId = "ruleId2",
                                message = SarifRunResultMessage(
                                    text = "message2",
                                ),
                                level = SarifRunResultSeverity.ERROR,
                                locations = listOf(
                                    SarifRunResultLocation(
                                        physicalLocation = SarifRunResultPhysicalLocation(
                                            artifactLocation = SarifRunResultArtifactLocation(
                                                uri = "file2",
                                            ),
                                            region = SarifRunResultLocationRegion(
                                                startLine = 5,
                                                startColumn = 6,
                                                endLine = 7,
                                                endColumn = 8,
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            )
            val detektReport = mapper.map(sarifRoot)
            detektReport shouldBe DetektReport(
                violations = listOf(
                    DetektViolation(
                        filePath = "file1",
                        message = "message",
                        ruleId = "ruleId",
                        location = DetektViolationLocation(
                            startLine = 1,
                            startColumn = 2,
                            endLine = 3,
                            endColumn = 4,
                        ),
                        severity = DetektViolationSeverity.WARNING,
                    ),
                    DetektViolation(
                        filePath = "file2",
                        message = "message2",
                        ruleId = "ruleId2",
                        location = DetektViolationLocation(
                            startLine = 5,
                            startColumn = 6,
                            endLine = 7,
                            endColumn = 8,
                        ),
                        severity = DetektViolationSeverity.ERROR,
                    ),
                ),
            )
        }
    },
)
