@file:Suppress("MemberVisibilityCanBePrivate")

package xyz.pavelkorolev.danger.detekt.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(namespace = "checkstyle")
data class DetektReport(

    @field:JsonProperty("file")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val files: List<DetektReportFile> = emptyList(),
) {

    /**
     * Returns violations number in all files of given report combined
     */
    val count: Int get() = files.fold(0) { acc, file -> acc + file.errors.size }

    /**
     * Returns true if there are 0 files inside or 0 errors inside all files
     */
    val isEmpty: Boolean get() = files.isEmpty() || count == 0
}
