package assignment1.problem2

import assignment1.problem2.OrderingObject.HandOrdering
import base.FileUtils

import java.io.FileNotFoundException
import scala.annotation.tailrec
import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success}

object Poker extends App {

  def apply(fileName: String, forPlayer: String = "player1"): Int = {
    val map = setupData(fileName)
    val p1 = new ListBuffer[Hand]()
    val p2 = new ListBuffer[Hand]()
    map.foreach(e => {
      p1 += Hand(e._2._1)
      p2 += Hand(e._2._2)
    })
    var player1Count = 0
    var player2Count = 0
    for (i <- p1.indices) {
      if (HandOrdering.compare(p1(i), p2(i)) < 0)
        player1Count += 1
      else
        player2Count += 1
    }
    if (forPlayer == "player1") player1Count else player2Count
  }

  def setupData(filename: String = "poker.txt"): ListMap[Int, (String, String)] = {
    val r = FileUtils.readTextFile(filename)
    r match {
      case Success(reader) => {
        val il = reader.getLines()
        @tailrec
        def fillMap(map: ListMap[Int, (String, String)] = ListMap(), key: Int = 0): ListMap[Int, (String, String)] =
          if (!il.hasNext) map
          else
            fillMap(map + (key -> {
              val line = il.next()
              (line.substring(0, 15), line.substring(15, line.length))
            }), key + 1)

        val map = fillMap()
        reader.close()
        map
      }
      case Failure(e) =>
        throw new FileNotFoundException(s"$filename not found")
    }
  }
}
