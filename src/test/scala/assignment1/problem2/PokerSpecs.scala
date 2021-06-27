package assignment1.problem2

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should

class PokerSpecs extends AnyFreeSpec with should.Matchers {
  "Poker - setupData " - {
    "should read a file and create a map with size 1000 entry" in {
      Poker.setupData().size should equal(1000)
    }
    "should read a file and create a map with size 5 entry" in {
      Poker.setupData("poker1.txt").size should equal(5)
    }
    "should read a file and create a map with size 20 entry" in {
      Poker.setupData("poker2.txt").size should equal(20)
    }
  }
  "Poker - calculate count of player " - {
    "should return 376 total winner for player1" in {
      Poker("poker.txt") should equal(376)
    }
    "should return 2 total winner for player1" in {
      Poker("poker1.txt") should equal(2)
    }
    "should return 27 total winner for player1" in {
      Poker("poker2.txt") should equal(8)
    }
    "should return 624 total winner for player2" in {
      Poker("poker.txt", "player2") should equal(624)
    }

    "should return 3 total winner for player2" in {
      Poker("poker1.txt", "player2") should equal(3)
    }

    "should return 12 total winner for player2" in {
      Poker("poker2.txt", "player2") should equal(12)
    }
  }

  "Poker - sum of player1 , player2 " - {
    "should be equal to 1000 as poker.txt have 1000 rows" in {
      val winner1Count = Poker("poker.txt")
      val winner2Count = Poker("poker.txt", "player2")
      (winner1Count + winner2Count) should equal(1000)
    }
    "should be equal to 5 as poker1.txt have 5 rows" in {
      val winner1Count = Poker("poker1.txt")
      val winner2Count = Poker("poker1.txt", "player2")
      (winner1Count + winner2Count) should equal(5)
    }
    "should be equal to 20 as poker.txt have 20 rows" in {
      val winner1Count = Poker("poker2.txt")
      val winner2Count = Poker("poker2.txt", "player2")
      (winner1Count + winner2Count) should equal(20)
    }
  }
}
