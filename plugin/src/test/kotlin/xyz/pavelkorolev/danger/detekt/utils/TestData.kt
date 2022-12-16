package xyz.pavelkorolev.danger.detekt.utils

object TestData {
    object CheckstyleReport {
        val empty = Resources.getFile("reports/report-empty.xml")
        val single = Resources.getFile("reports/report-single.xml")
        val multiple = Resources.getFile("reports/report-multiple.xml")
        val malformed = Resources.getFile("reports/report-malformed.xml")
    }
    object SarifReport {
        val multiple = Resources.getFile("reports/report-multiple.sarif")
    }
}
