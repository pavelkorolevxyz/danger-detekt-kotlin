package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRunResultLocationRegion(

    @JsonProperty("startLine")
    val startLine: Int? = null,

    @JsonProperty("startColumn")
    val startColumn: Int? = null,

    @JsonProperty("endLine")
    val endLine: Int? = null,

    @JsonProperty("endColumn")
    val endColumn: Int? = null,
)
