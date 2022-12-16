package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRoot(

    @JsonProperty("runs")
    val runs: List<SarifRun> = emptyList(),
)
