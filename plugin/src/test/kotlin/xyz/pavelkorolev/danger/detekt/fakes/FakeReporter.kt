package xyz.pavelkorolev.danger.detekt.fakes

import xyz.pavelkorolev.danger.detekt.DetektViolationReporter
import xyz.pavelkorolev.danger.detekt.model.DetektViolation

internal class FakeReporter : DetektViolationReporter {

    val outputs = mutableListOf<DetektViolation>()

    override fun report(violation: DetektViolation) {
        outputs.add(violation)
    }
}
