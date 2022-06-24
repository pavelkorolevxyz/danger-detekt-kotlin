package xyz.pavelkorolev.danger.detekt.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class DetektErrorSeverity {

    @JsonProperty("info")
    INFO,

    @JsonProperty("warning")
    WARNING,

    @JsonProperty("error")
    ERROR,
}
