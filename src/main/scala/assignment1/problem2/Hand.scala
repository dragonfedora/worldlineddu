package assignment1.problem2

import assignment1.problem2.Cons.ranks
import assignment1.problem2.FunctionPredicate._
import assignment1.problem2.OrderingObject.ValueOrdering
import assignment1.problem2.RankUtils._

object Hand{
  def apply(handStr: String): Hand = new Hand(handStr)
}

class Hand(handStr: String) extends Comparable[Hand] {

  var winnerHands: List[Card] = List()
  implicit val faces: Array[String] = handStr.split(" ")
  val hands = addHand()
  implicit val sortedHand = hands.sorted(ValueOrdering)

  val suitsNo: Int = totalSuits()
  val valuesNo: Int = totalValues()
  val noOfPairs: Int = pairOrTriple(ifEqualTo2)
  val noOfThrees: Int = pairOrTriple(ifEqualTo3)

  def getRanks: Int = {
    if (isRoyalFlush())
      ranks.indexOf("royal-flush")
    else if (isStraightFlush())
      ranks.indexOf("straight-flush")
    else if (isFourOrFullHouse(isFour, ifEqualTo4, valuesNo, noOfPairs, noOfThrees))
      ranks.indexOf("four")
    else if (isFourOrFullHouse(isFullHouse, ifEqualTo2, valuesNo, noOfPairs, noOfThrees))
      ranks.indexOf("full-house")
    else if (isFlush()._1) {
      winnerHands = isFlush()._2
      ranks.indexOf("flush")
    } else if (isStraight()._1) {
      winnerHands = isStraight()._2
      ranks.indexOf("straight")
    } else if (isXOfKind(noOfThrees, ifEqualTo2, isGT0)._1) {
      winnerHands = isXOfKind(noOfThrees, ifEqualTo2, isGT0)._2
      ranks.indexOf("three")
    } else if (isXOfKind(noOfPairs, ifEqualTo1, ifEqualTo2)._1) {
      winnerHands = isXOfKind(noOfPairs, ifEqualTo1, ifEqualTo2)._2
      ranks.indexOf("two")
    } else if (isXOfKind(noOfPairs, ifEqualTo1, ifEqualTo1)._1) {
      winnerHands = isXOfKind(noOfPairs, ifEqualTo1, ifEqualTo1)._2
      ranks.indexOf("one")
    }
    else {
      winnerHands = hands
      ranks.indexOf("high")
    }
  }

  override def compareTo(h2: Hand): Int = {
    val h1Rank = this.getRanks
    val h2Rank = h2.getRanks
    if (h1Rank == h2Rank) {
      this.winnerHands.sorted(ValueOrdering).foreach(h1 => h2.winnerHands.sorted(ValueOrdering).foreach(h2 => {
        if (h1.value != h2.value)
          return ValueOrdering.compare(h1, h2)
      }))
      this.sortedHand.foreach(h1 => h2.sortedHand.foreach(h2 => {
        if (h1.value != h2.value)
          return ValueOrdering.compare(h1, h2)
      }))
      0
    }
    else
      h2Rank - h1Rank
  }
}



