package assignment1.problem1

import base.DateUtils

import java.text.ParseException
import java.time.LocalDate
import scala.annotation.tailrec

object DaysCalculator {

  def process(startYear: Int = 1901, startMonth: Int = 1, startDay: Int = 1, endYear: Int = 2000, endMonth: Int = 12, endDay: Int = 31, day: String = "SUNDAY"): Int = {
    @tailrec
    def start(y: Int, count: Int = 0): Int =
      if (y > endYear) count
      else {
        val month = if (y == startYear) startMonth else 1
        start(y + 1, countDays(m = month, y = y, acc = count))
      }

    @tailrec
    def countDays(m: Int = startMonth, y: Int, acc: Int = 0): Int =
      if (m > 12 || (m >= endMonth && y >= endYear)) acc
      else if (LocalDate.of(y, m, startDay).getDayOfWeek().name() == day.toUpperCase())
        countDays(m + 1, y, acc + 1)
      else
        countDays(m + 1, y, acc)

    if (startYear > endYear)
      throw new InvalidDateException("[Invalid End Date] - End date can not be greater than start date")

    start(startYear)
  }

  def process(startDate: String, endDate: String, day: String): Int = {
    try {
      val sd = DateUtils.yyyymmddParse(startDate.replaceAll(" ", ""))
      val ed = DateUtils.yyyymmddParse(endDate.replaceAll(" ", ""))
      process(sd.getYear, sd.getMonthValue, sd.getDayOfMonth, ed.getYear, ed.getMonthValue, ed.getDayOfMonth, day)
    }
    catch {
      case e: ParseException => throw new ParseException(s"${e.getMessage}, allowed format like 01 jan 1901 or 01jan1901", 0)
    }
  }
}

class InvalidDateException(message: String) extends RuntimeException(message)