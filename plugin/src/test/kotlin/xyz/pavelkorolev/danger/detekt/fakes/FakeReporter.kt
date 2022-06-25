package xyz.pavelkorolev.danger.detekt.fakes

import xyz.pavelkorolev.danger.detekt.DetektErrorReporter
import xyz.pavelkorolev.danger.detekt.model.DetektError

internal class FakeReporter : DetektErrorReporter {

    val outputs = mutableListOf<DetektError>()

    override fun report(error: DetektError, fileName: String?) {
        outputs.add(error)
    }
}
