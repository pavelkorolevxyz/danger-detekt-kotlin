package xyz.pavelkorolev.danger.detekt.parser.checkstyle.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

internal data class CheckstyleError(

    @field:JacksonXmlProperty
    val column: Int? = null,

    @field:JacksonXmlProperty
    val line: Int? = null,

    @field:JacksonXmlProperty
    val message: String? = null,

    @field:JacksonXmlProperty
    val severity: CheckstyleErrorSeverity? = null,

    @field:JacksonXmlProperty
    val source: String? = null,
)
