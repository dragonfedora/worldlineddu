package assignment1.problem1

import org.scalatest.flatspec.AnyFlatSpec

import java.time.format.DateTimeParseException

class DaysCalculatorSpecs extends AnyFlatSpec{

  it should "count Sundays between dates" in {
    assert(DaysCalculator.process("01 Jan 1901", "31 Dec 2000", "SUNDAY") == 171)
    assert(DaysCalculator.process("01 Jan 2019", "01 Dec 2020", "SUNDAY") == 4)
    assert(DaysCalculator.process("01 Sep 2019", "01 Jul 2020", "SUNDAY") == 3)
  }

  it should "count Mondays between dates" in {
    assert(DaysCalculator.process("01 Sep 2019", "01 Jan 2020", "MONDAY") == 0)
    assert(DaysCalculator.process("01 Jan 2019", "01 Dec 2020", "MONDAY") == 3)
    assert(DaysCalculator.process("01 Jan 2019", "01 Dec 2020", "MONDAY") == 3)
  }

  it should "throw InvalidDateException" in
    assertThrows[InvalidDateException]{
      DaysCalculator.process("01 Sep 2019", "01 Jan 2018", "MONDAY")
    }

  it should "throw DateTimeParseException" in
    assertThrows[DateTimeParseException]{
      DaysCalculator.process("01 Sep 2019", "01Ja 2018", "MONDAY")
    }


}
