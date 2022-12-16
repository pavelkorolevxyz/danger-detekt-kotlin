package xyz.pavelkorolev.danger.detekt.parser.checkstyle

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import xyz.pavelkorolev.danger.detekt.parser.checkstyle.model.CheckstyleRoot
import java.io.File

internal class CheckstyleReportParser {

    private val xmlMapper = XmlMapper().apply {
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    private val checkstyleMapper = CheckstyleReportMapper()

    fun parse(file: File): DetektReport {
        val checkstyleRoot: CheckstyleRoot = xmlMapper.readValue(file)
        return checkstyleMapper.map(checkstyleRoot)
    }

    private inline fun <reified T> XmlMapper.readValue(file: File): T =
        readValue(file, T::class.java)
}
