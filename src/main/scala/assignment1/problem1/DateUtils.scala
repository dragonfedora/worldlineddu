package assignment1.problem1

import java.time._
import java.time.format.DateTimeFormatter

object DateUtils {

  def ddmmmyyyyFormat: DateTimeFormatter =
    DateTimeFormatter.ofPattern("ddMMMyyyy")

  def yyyymmddParse(dt: String): LocalDate = LocalDate.parse(dt, ddmmmyyyyFormat)
}
