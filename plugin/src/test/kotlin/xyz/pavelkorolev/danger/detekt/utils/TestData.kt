package xyz.pavelkorolev.danger.detekt.utils

object TestData {
    val emptyReportFile = Resources.getFile("reports/report-empty.xml")
    val singleReportFile = Resources.getFile("reports/report-single.xml")
    val multipleReportFile = Resources.getFile("reports/report-multiple.xml")
    val malformedReportFile = Resources.getFile("reports/report-malformed.xml")
}
