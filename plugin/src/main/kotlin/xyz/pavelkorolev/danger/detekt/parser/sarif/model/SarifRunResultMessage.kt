package xyz.pavelkorolev.danger.detekt.parser.sarif.model

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SarifRunResultMessage(

    @JsonProperty("text")
    val text: String? = null,
)
