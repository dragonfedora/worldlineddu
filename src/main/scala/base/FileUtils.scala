package base

import scala.io.{BufferedSource, Source}
import scala.util.Try

object FileUtils {
  def readTextFile(filename: String): Try[BufferedSource] = {
    Try(Source.fromResource(filename))
  }
}
