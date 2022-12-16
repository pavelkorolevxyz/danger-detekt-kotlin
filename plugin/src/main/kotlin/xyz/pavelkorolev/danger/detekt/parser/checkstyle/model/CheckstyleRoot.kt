@file:Suppress("MemberVisibilityCanBePrivate")

package xyz.pavelkorolev.danger.detekt.parser.checkstyle.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(namespace = "checkstyle")
internal data class CheckstyleRoot(

    @field:JsonProperty("file")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val files: List<CheckstyleFile> = emptyList(),
)
