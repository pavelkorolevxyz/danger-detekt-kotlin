package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRunResultLocation(

    @JsonProperty("physicalLocation")
    val physicalLocation: SarifRunResultPhysicalLocation? = null,
)
