package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRunResult(

    @JsonProperty("level")
    val level: SarifRunResultSeverity? = null,

    @JsonProperty("message")
    val message: SarifRunResultMessage? = null,

    @JsonProperty("ruleId")
    val ruleId: String? = null,

    @JsonProperty("locations")
    val locations: List<SarifRunResultLocation> = emptyList(),
)
