package xyz.pavelkorolev.danger.detekt.fakes

import systems.danger.kotlin.sdk.DangerContext
import systems.danger.kotlin.sdk.Violation

internal class FakeDangerContext : DangerContext {

    override val fails = mutableListOf<Violation>()
    override val markdowns = mutableListOf<Violation>()
    override val messages = mutableListOf<Violation>()
    override val warnings = mutableListOf<Violation>()

    val violationsCount: Int get() = fails.size + markdowns.size + messages.size + warnings.size

    override fun fail(message: String) {
        fails.add(Violation(message))
    }

    override fun fail(message: String, file: String, line: Int) {
        fails.add(Violation(message, file, line))
    }

    override fun markdown(message: String) {
        markdowns.add(Violation(message))
    }

    override fun markdown(message: String, file: String, line: Int) {
        markdowns.add(Violation(message, file, line))
    }

    override fun message(message: String) {
        messages.add(Violation(message))
    }

    override fun message(message: String, file: String, line: Int) {
        messages.add(Violation(message, file, line))
    }

    override fun suggest(code: String, file: String, line: Int) {
        throw NotImplementedError()
    }

    override fun warn(message: String) {
        warnings.add(Violation(message))
    }

    override fun warn(message: String, file: String, line: Int) {
        warnings.add(Violation(message, file, line))
    }
}
