package assignment1.problem2

import assignment1.problem2.Cons.values

import scala.annotation.tailrec

case class Card(suit: String, value: String)

object RankUtils {

  @tailrec
  def addHand(i: Int = 0, hand: List[Card] = List())(implicit faces: Array[String]): List[Card] =
    if (i >= faces.length) hand
    else addHand(i + 1, hand :+ Card(faces(i)(1).toString, faces(i)(0).toString))

  def totalSuits()(implicit hands: List[Card]): Int =
    hands.flatMap(c => Set() + c.suit).toSet.size

  def totalValues()(implicit hands: List[Card]): Int =
    hands.flatMap(c => Set() + c.value).toSet.size

  def pairOrTriple(f: (Int) => Boolean)(implicit hands: List[Card]): Int =
    hands.flatMap(h => h.value)
      .groupBy(identity)
      .mapValues(_.size).count(x => f(x._2))

  @tailrec
  def isRoyalFlush(i: Int = 0, count: Int = 0)(implicit hands: List[Card]): Boolean =
    if (i > hands.size - 1 && count < 5) false
    else if (count == 5) true
    else if (hands.head.suit != hands(i).suit) false
    else hands(i).value match {
      case "A" | "K" | "Q" | "J" | "T" => isRoyalFlush(i + 1, count + 1)
      case _ => isRoyalFlush(i + 1, count)
    }

  /*
  * 1. TC 9C 8C 7C 6C - true
  * 2. TC 9H 8C 7C 6C - false
  * */
  def isStraightFlush()(implicit hands: List[Card]): Boolean =
    isStraight()._1 && isFlush()._1

  /*
  * 1. Four - TC 9H 9C 9S 9D = true
  * 2. Full House - 8C 8H 8C AS AD = true
  * */
  def isFourOrFullHouse(p: (Int, Int, Int) => Boolean, f: (Int) => Boolean, nv: Int, np: Int, nt: Int)(implicit hands: List[Card]): Boolean =
    if (p(nv, np, nt))
      hands.flatMap(x => List() :+ x.value)
        .groupBy(identity)
        .mapValues(_.size)
        .exists(e => f(e._2))
    else false

  /*
  * 8C 2C 1C 6C KC = true
  * 8C 8H 4C AS AD = false
  * */
  def isFlush()(implicit hands: List[Card]): (Boolean, List[Card]) =
    if (hands.flatMap(x => Set() + x.suit).toSet.size == 1)
      (true, List.concat(List(), hands))
    else (false, List())

  /*
  *TC 9H 8D 7S 6C - true
  * 8C 8H 4C AS AD = false
   */
  def isStraight()(implicit hands: List[Card]): (Boolean, List[Card]) = {
    var previousIndex = values.indexOf(hands.head.value)
    var index = 0
    for (i <- 1 until hands.size) {
      index = values.indexOf(hands(i).value)
      if (index != previousIndex - 1) return (false, List())
      previousIndex = index
    }
    (true, List.concat(List(), hands))
  }

  /*
  Three of Kind - 8C 8H 8D 7S 6C = true
  TwoPair - 8C 8H 7D 7S 6C = true
  One Pair - AC AH KD JS 6C = true
   */
  def isXOfKind(noOfx: Int, f: (Int) => Boolean, p: (Int) => Boolean)(implicit hands: List[Card]): (Boolean, List[Card]) = {
    var l: List[Card] = List()
    if (p(noOfx)) {
      for (i <- hands.indices) {
        var count = 0
        for (j <- hands.indices) {
          if (i == j) 0
          else if (hands(i).value == hands(j).value)
            count += 1
        }
        if (f(count))
          l = l :+ hands(i)
      }
      (true, l)
    }
    else
      (false, l)
  }
}
