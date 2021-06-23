package assignment1.problem3

import scala.annotation.tailrec
import scala.collection.immutable.ListMap
import scala.io.Source

object TriangleProblem extends App {

  def process(filename: Option[String] = Some("triangle.txt")): Int = {

    val map = setupData(filename.getOrElse("triangle.txt"))
    val vSize = map.size
    val hSize = map(vSize - 1)
    val aa = transform(map, vSize, hSize.length)

    def maxSum(arr: Array[Array[Int]], m: Int, n: Int) = {
      for (i <- m - 1 to 0 by -1)
        for (j <- 0 to i)
          if (arr(i + 1)(j) > arr(i + 1)(j + 1))
            arr(i)(j) += arr(i + 1)(j);
          else
            arr(i)(j) += arr(i + 1)(j + 1);

      arr(0)(0)
    }

    maxSum(aa, map.size - 1, map.size - 1)
  }

  def setupData(filename: String): ListMap[Int, Array[Int]] = {
    val r = Source.fromResource(filename)
    val il = r.getLines()

    @tailrec
    def fillMap(map: ListMap[Int, Array[Int]] = ListMap(), key: Int = 0): ListMap[Int, Array[Int]] = {
      if(!il.hasNext) map
      else fillMap(map + (key -> il.next().split(" ").map(x => x.toInt)), key + 1)
    }

    val map = fillMap()
    r.close()
    map
  }

  def transform(map: ListMap[Int, Array[Int]], vSize: Int, hSize: Int) = {
    val aa: Array[Array[Int]] = Array.ofDim(vSize, hSize)
    for(i <- 0 to map.size - 1){
      val temp = Array.fill(hSize)(0)
      for(j <- 0 to map.getOrElse(i, Array()).size - 1){
        temp(j) = map.getOrElse(i, Array())(j)
      }
      aa.update(i, temp)
    }
    aa
  }
}
