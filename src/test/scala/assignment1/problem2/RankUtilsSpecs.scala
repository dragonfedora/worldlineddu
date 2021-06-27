package assignment1.problem2

import assignment1.problem2.FunctionPredicate.{ifEqualTo1, ifEqualTo2, ifEqualTo3, ifEqualTo4, isFour, isFullHouse, isGT0}
import assignment1.problem2.RankUtils.{pairOrTriple, totalValues}
import org.scalatest.freespec.AnyFreeSpec

class RankUtilsSpecs extends AnyFreeSpec {

  "addHand : convert list of literals to Card list" - {
    "test1" in {
      implicit val faces = "8C TS KC 9H 4S".split(" ")
      assert(RankUtils.addHand().nonEmpty)
    }
  }

  "totalSuits : count suits in hand" - {
    "test1" in {
      val faces = "8C TS KC 9H 4S".split(" ")
      val hands = RankUtils.addHand()(faces)
      assert(RankUtils.totalSuits()(hands) == 3)
    }
    "test2" in {
      val faces = "8H TD KD 9H 4H".split(" ")
      val hands = RankUtils.addHand()(faces)
      assert(RankUtils.totalSuits()(hands) == 2)
    }
    "test3" in {
      val faces: Array[String] = "8C TS KH 9D 4S".split(" ")
      val hands = RankUtils.addHand()(faces)
      assert(RankUtils.totalSuits()(hands) == 4)
    }
  }

  "totalValue : count value faces in hand" - {
    "test1" in {
      implicit val faces = "8C TS KC 9H 4S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.totalValues() == 5)
    }

    "test2" in {
      implicit val faces = "8C 8S KC 9H 4S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.totalValues() == 4)
    }
  }

  "Count Pairs - pairOrTriple : count pairs in hand" - {
    "test1" in {
      implicit val faces = "8C 8S KC 9H 4S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.pairOrTriple(ifEqualTo2) == 1)
    }

    "test2" in {
      implicit val faces = "8C 8S 7C 7H 4S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.pairOrTriple(ifEqualTo2) == 2)
    }

    "test3" in {
      implicit val faces = "8C 8S 8C 7H 4S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.pairOrTriple(ifEqualTo2) == 0)
    }
    "test4" in {
      implicit val faces = "8C 8S 8C 9H 9S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.pairOrTriple(ifEqualTo2) == 1)
    }
  }
  "Count Triple -  pairOrTriple : count triple in hand" - {
    "test1" in {
      implicit val faces = "8C 8S 8C 7H 4S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.pairOrTriple(ifEqualTo3) == 1)
    }
    "test2" in {
      implicit val faces = "8C 8S 2C 9H 9S".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.pairOrTriple(ifEqualTo3) == 0)
    }
  }

  "Royal-Flush - isRoyalFlush  : check if hand has royal-flush faces" - {
    "test-1 AC KC QC JC TC - should return true" in {
      implicit val faces = "AC KC QC JC TC".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.isRoyalFlush())
    }
    "test-2 AC KC QC JC 9C - should return false" in {
      implicit val faces = "AC KC QC JC 9C".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(!RankUtils.isRoyalFlush())
    }
  }

  "Straight-Flush - isStraightFlush  : check if hand has straight-flush faces" - {
    "test-1 TC 9C 8C 7C 6C - should return true" in {
      implicit val faces = "TC 9C 8C 7C 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.isStraightFlush())
    }
    "test-2 TC 9H 8C 7C 6C - should return false" in {
      implicit val faces = "TC 9H 8C 7C 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(!RankUtils.isStraightFlush())
    }
  }

  "Four-Of-Kind - isFourOrFullHouse  : check if hand has four-of-kind faces" - {
    "test-1 TC 9H 9C 9S 9D - should return true" in {
      implicit val faces = "TC 9H 9C 9S 9D".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.isFourOrFullHouse(isFour, ifEqualTo4, 2, 1, 0))
    }
    "test-2 TC 9H 9C 9S 9H - should return false" in {
      implicit val faces = "TC 9H 8C 7C 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(!RankUtils.isFourOrFullHouse(isFour, ifEqualTo4, 2, 1, 0))
    }
  }

  "Full House - isFourOrFullHouse  : check if hand has full-house faces" - {
    "test-1 8C 8H 8C AS AD - should return true" in {
      implicit val faces = "8C 8H 8C AS AD".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.isFourOrFullHouse(isFullHouse, ifEqualTo2, totalValues(), pairOrTriple(ifEqualTo2), pairOrTriple(ifEqualTo3)))
    }
    "test-2 8C 8H 8C AS AD - should return true" in {
      implicit val faces = "8C 8H 8C AS AD".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(RankUtils.isFourOrFullHouse(isFullHouse, ifEqualTo2, totalValues(), pairOrTriple(ifEqualTo2), pairOrTriple(ifEqualTo3)))
    }
    "test-3 8C 8H 4C AS AD - should return false" in {
      implicit val faces = "8C 8H 4C AS AD".split(" ")
      implicit val hands = RankUtils.addHand()
      assert(!RankUtils.isFourOrFullHouse(isFullHouse, ifEqualTo2, totalValues(), pairOrTriple(ifEqualTo2), pairOrTriple(ifEqualTo3)))
    }
  }
  "Flush - isFlush  : check if hand has flush faces" - {
    "test-1 8C 2C 1C 6C KC - should return true" in {
      implicit val faces = "8C 2C 1C 6C KC".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isFlush()
      assert(r._1)
      assert((r._2).size == 5)
    }
    "test-2 KC QC TC 8C 6C - should return true" in {
      implicit val faces = "KC QC TC 8C 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isFlush()
      assert(r._1)
      assert((r._2).size == 5)
    }
    "test-3 8C 8H 4C AS AD - should return false" in {
      implicit val faces = "8C 8H 4C AS AD".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isFlush()
      assert(!r._1)
      assert((r._2).size != 5)
    }
  }

  "Straight - isStraight  : check if hand has straight faces" - {
    "test-1 TC 9H 8D 7S 6C - should return true" in {
      implicit val faces = "TC 9H 8D 7S 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isStraight()
      assert(r._1)
      assert((r._2).size == 5)
    }
    "test-3 8C 8H 4C AS AD - should return false" in {
      implicit val faces = "8C 8H 4C AS AD".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isStraight()
      assert(!r._1)
      assert((r._2).size != 5)
    }
  }

  "Three Of Kind - isXOfKind  : check if hand has three-of-kind faces" - {
    "test-1 8C 8H 8D 7S 6C - should return true" in {
      implicit val faces = "8C 8H 8D 7S 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo3), ifEqualTo2, isGT0)
      assert(r._1)
      assert((r._2).size == 3)
    }
    "test-2 AC AH AD 7S 6C - should return true" in {
      implicit val faces = "AC AH AD 7S 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo3), ifEqualTo2, isGT0)
      assert(r._1)
      assert((r._2).size == 3)
    }
    "test-3 8C 8H 4C 8S 8D - should return false" in {
      implicit val faces = "8C 8H 4C AS AD".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo3), ifEqualTo2, isGT0)
      assert(!r._1)
      assert((r._2).size !== 3)
    }
  }
  "Two Pair - isXOfKind  : check if hand has two-pair faces" - {
    "test-1 8C 8H 7D 7S 6C - should return true" in {
      implicit val faces = "8C 8H 7D 7S 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo2), ifEqualTo1, ifEqualTo2)
      assert(r._1)
      assert((r._2).size == 4)
    }
    "test-2 AC AH KD KS 6C - should return true" in {
      implicit val faces = "AC AH KD KS 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo2), ifEqualTo1, ifEqualTo2)
      assert(r._1)
      assert((r._2).size == 4)
    }
    "test-3 8C 8H 4C 8S 7D - should return false" in {
      implicit val faces = "8C 8H 4C AS 7D".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo3), ifEqualTo2, isGT0)
      assert(!r._1)
      assert((r._2).size !== 3)
    }
  }
  "One Pair - isXOfKind  : check if hand has one-pair faces" - {
    "test-1 8C 8H 7D 5S 6C - should return true" in {
      implicit val faces = "8C 8H 7D 5S 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo2), ifEqualTo1, ifEqualTo1)
      assert(r._1)
      assert((r._2).size == 2)
    }
    "test-2 AC AH KD JS 6C - should return true" in {
      implicit val faces = "AC AH KD JS 6C".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo2), ifEqualTo1, ifEqualTo1)
      assert(r._1)
      assert((r._2).size == 2)
    }
    "test-3 8C 8H 4C 4S 7D - should return false" in {
      implicit val faces = "8C 8H 4C 4S 7D".split(" ")
      implicit val hands = RankUtils.addHand()
      val r = RankUtils.isXOfKind(pairOrTriple(ifEqualTo2), ifEqualTo1, ifEqualTo1)
      assert(!r._1)
      assert((r._2).size !== 2)
    }
  }
}
