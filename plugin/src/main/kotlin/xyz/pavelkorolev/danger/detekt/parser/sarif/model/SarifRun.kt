package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRun(

    @JsonProperty("results")
    val results: List<SarifRunResult> = emptyList(),
)
