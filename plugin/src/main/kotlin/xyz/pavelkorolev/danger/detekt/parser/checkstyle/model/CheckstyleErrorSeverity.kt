package xyz.pavelkorolev.danger.detekt.parser.checkstyle.model

import com.fasterxml.jackson.annotation.JsonProperty

internal enum class CheckstyleErrorSeverity {

    @JsonProperty("info")
    INFO,

    @JsonProperty("warning")
    WARNING,

    @JsonProperty("error")
    ERROR,
}
