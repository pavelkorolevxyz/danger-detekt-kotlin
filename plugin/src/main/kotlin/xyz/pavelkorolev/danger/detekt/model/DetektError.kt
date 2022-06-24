package xyz.pavelkorolev.danger.detekt.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class DetektError(

    @field:JacksonXmlProperty
    val column: Int? = null,

    @field:JacksonXmlProperty
    val line: Int? = null,

    @field:JacksonXmlProperty
    val message: String? = null,

    @field:JacksonXmlProperty
    val severity: DetektErrorSeverity? = null,

    @field:JacksonXmlProperty
    val source: String? = null,
)
