package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRunResultPhysicalLocation(

    @JsonProperty("artifactLocation")
    val artifactLocation: SarifRunResultArtifactLocation? = null,

    @JsonProperty("region")
    val region: SarifRunResultLocationRegion? = null,
)
