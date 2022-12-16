package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRunResultArtifactLocation(

    @JsonProperty("uri")
    val uri: String? = null,
)
