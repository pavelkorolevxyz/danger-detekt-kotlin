package xyz.pavelkorolev.danger.detekt

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import xyz.pavelkorolev.danger.detekt.model.DetektReport
import java.io.File

/**
 * Class which encapsulate detekt report XML deserialization logic
 */
internal class DetektReportParser {

    private val xmlMapper = XmlMapper().apply {
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    /**
     * Parses all given XML files and returns [DetektReport]
     */
    fun parse(files: List<File>): DetektReport {
        val reportFiles = files
            .map(::parse)
            .flatMap { it.files }
        return DetektReport(
            files = reportFiles,
        )
    }

    private fun parse(file: File): DetektReport =
        xmlMapper.readValue(file)

    private inline fun <reified T> XmlMapper.readValue(file: File): T =
        readValue(file, T::class.java)
}
