package assignment1.problem2

import assignment1.problem2.Cons.ranks
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should

class HandSpecs extends AnyFreeSpec with should.Matchers {
  "Hand Instance " - {
    "should short the hands and calculate suits, values, pairs, triple" in {
      val face = "5H QS 8S 6D 3C"
      val h = Hand(face)
      h.hands should equal(List(Card("H", "5"), Card("S", "Q"), Card("S", "8"), Card("D", "6"), Card("C", "3")))
      h.sortedHand should equal(List(Card("S", "Q"), Card("S", "8"), Card("D", "6"), Card("H", "5"), Card("C", "3")))
      h.suitsNo should equal(4)
      h.valuesNo should equal(5)
      h.noOfPairs should equal(0)
      h.noOfThrees should equal(0)
    }
  }
  "Hand - getRank() " - {
    "should return correct rank" in {
      Hand("AC KC QC JC TC").getRanks should equal(ranks.indexOf("royal-flush"))
      Hand("TC 9C 8C 7C 6C").getRanks should equal(ranks.indexOf("straight-flush"))
      //      Hand("TC 9H 9C 9S 9D").getRanks should equal(ranks.indexOf("four"))
      Hand("8C 8H 8C AS AD").getRanks should equal(ranks.indexOf("full-house"))
      Hand("8C 2C 1C 6C KC").getRanks should equal(ranks.indexOf("flush"))
      Hand("TC 9H 8D 7S 6C").getRanks should equal(ranks.indexOf("straight"))
      Hand("8C 8H 8D 7S 6C").getRanks should equal(ranks.indexOf("three"))
      Hand("8C 8H 7D 7S 6C").getRanks should equal(ranks.indexOf("two"))
      Hand("8C 8H 7D 5S 6C").getRanks should equal(ranks.indexOf("one"))
      Hand("KC JS 7H 6S 3H").getRanks should equal(ranks.indexOf("high"))

    }
  }

  "High-Hand - compareTo() " - {
    "compare two high hands - player1 should be winner" in {
      player1WinnerTest(p1 = "KC JS 7H 6S 3H", p2 = "QH JC TD 8S 2D")
    }
    "compare two high hands - player2 should be winner" in {
      player2WinnerTest(p1 = "KC JS 7H 6S 3H", p2 = "AH JC TD 8S 2D")
    }
  }

  "Pair-Hand - compareTo() " - {
    "compare pair hands - player1 should be winner" in {
      player1WinnerTest(p1 = "KC KS 7H 6S 3H", p2 = "QH QC TD 8S 2D")
    }
    "compare pair hands hands - player2 should be winner" in {
      player2WinnerTest(p1 = "KC KS 7H 6S 3H", p2 = "AH AC TD 8S 2D")
    }
  }
  "Two-Pair-Hand - compareTo() " - {
    "compare two-pairs hands - player1 should be winner" in {
      player1WinnerTest(p1 = "KC KS 7H 7S 3H", p2 = "QH QC 6D 6S 2D")
    }
    "compare two-pairs hands - player2 should be winner" in {
      player2WinnerTest(p1 = "QH QC 6D 6S 2D", p2 = "AH AC 9D 9S 2D")
    }
  }
  "Three-Of-Kind - compareTo() " - {
    "compare three of kinds hands - player1 should be winner" in {
      player1WinnerTest(p1 = "KC KS KH 7S 3H", p2 = "QH QC QD 6S 2D")
    }
    "compare three of kinds hands - player2 should be winner" in {
      player2WinnerTest(p1 = "KC KS KH 7S 3H", p2 = "AH AC AD 8S 2D")
    }
  }
  "Straight - compareTo() " - {
    "compare straight hands - player1 should be winner" in {
      player1WinnerTest(p1 = "TC 9H 8D 7S 6C", p2 = "9H 8D 7S 6C 5C")
    }
    "compare straight hands - player2 should be winner" in {
      player2WinnerTest(p1 = "TC 9H 8D 7S 6C", p2 = "JH TC 9H 8D 7S")
    }
  }
  "Flush - compareTo() " - {
    "compare flush hands - player1 should be winner" in {
      player1WinnerTest(p1 = "8C 2C 1C 6C KC", p2 = "7C 2C 1C 6C KC")
    }
    "compare flush hands - player2 should be winner" in {
      player2WinnerTest(p1 = "8C 2C 1C 6C KC", p2 = "9C 3C 4C 6C AC")
    }
  }
  "FlushHouse : compareTo() " - {
    "compare flush hands - player1 should be winner" in {
      player1WinnerTest(p1 = "8C 8H 8S AS AD", p2 = "7C 7D 7S AS AD")
    }
    "compare flush hands - player2 should be winner" in {
      player2WinnerTest(p1 = "7C 7H 7D 6C 6D", p2 = "9C 9H 9D 6C 6C")
    }
    "compare flush hands with pair win hand- player2 should be winner" in {
      player2WinnerTest(p1 = "7C 7H 7D 8C 8D", p2 = "7H 7D 7S TC TD")
    }
  }
  "FourOfKind : compareTo() " - {
    "compare four-of-kind - player1 should be winner" in {
      player1WinnerTest(p1 = "TC 9H 9C 9S 9D", p2 = "TC 8H 8C 8S 8D")
    }
    "compare four-of-kind - player2 should be winner" in {
      player2WinnerTest(p1 = "TC 7H 7C 7S 7D", p2 = "KC 8H 8C 8S 8D")
    }
  }
  "StraightFlush : compareTo() " - {
    "compare straight-flush - player1 should be winner" in {
      player1WinnerTest(p1 = "TC 9C 8C 7C 6C", p2 = "8D 7D 6D 5D 4D")
    }
    "compare straight-flush - player2 should be winner" in {
      player2WinnerTest(p1 = "TC 9C 8C 7C 6C", p2 = "QC JC TC 9C 8C")
    }
  }

  private def player1WinnerTest(p1: String, p2: String) = {
    val h1 = Hand(p1)
    val h2 = Hand(p2)
    h1.getRanks should equal(h2.getRanks)
    h1.compareTo(h2) should be < 0
  }

  private def player2WinnerTest(p1: String, p2: String) = {
    val h1 = Hand(p1)
    val h2 = Hand(p2)
    h1.getRanks should equal(h2.getRanks)
    h1.compareTo(h2) should be > 0
  }
}
