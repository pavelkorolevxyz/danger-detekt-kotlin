package xyz.pavelkorolev.danger.detekt.parser.sarif

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.parser.sarif.model.SarifRoot
import java.io.File

internal class SarifReportParser {

    private val jsonMapper = ObjectMapper()
        .registerKotlinModule()
        .apply {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }

    private val sarifReportMapper = SarifReportMapper()

    fun parse(file: File): DetektReport {
        val sarifReport: SarifRoot = jsonMapper.readValue(file)
        return sarifReportMapper.map(sarifReport)
    }
}
