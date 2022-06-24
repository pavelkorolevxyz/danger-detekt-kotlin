package xyz.pavelkorolev.danger.detekt.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class DetektReportFile(

    @field:JacksonXmlProperty
    val name: String? = null,

    @field:JsonProperty("error")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val errors: List<DetektError> = emptyList(),
)
