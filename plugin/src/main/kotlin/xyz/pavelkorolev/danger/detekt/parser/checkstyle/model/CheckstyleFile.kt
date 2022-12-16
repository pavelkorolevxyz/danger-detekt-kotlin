package xyz.pavelkorolev.danger.detekt.parser.checkstyle.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

internal data class CheckstyleFile(

    @field:JacksonXmlProperty
    val name: String? = null,

    @field:JsonProperty("error")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val errors: List<CheckstyleError> = emptyList(),
)
