package assignment2

import akka.actor.{Actor, ActorLogging}
trait Shape {
  def area(): Int
}
object Widgets {
  case class Rectangle(px: Int, py: Int, width: Int, height: Int) extends Shape {
    override def area() = width * height
  }
  case class Square(px: Int, py: Int, width: Int) extends Shape {
    override def area() = width * width
  }
  case class Ellipse(pX: Int, pY: Int, hd: Int, vd: Int) extends Shape {
    override def area() = (Math.PI * hd / 2 * vd / 2).toInt
  }
  case class Circle(pX: Int, pY: Int, diameter: Int) extends Shape {
    override def area() = ((Math.PI * (diameter / 2) * (diameter / 2))).toInt
  }
  case class TextBox(pX: Int, pY: Int, width: Int, height: Int, text: Option[String]) extends Shape {
    override def area() = width * height
  }
}

class Widgets extends Actor() with ActorLogging {

  import Widgets._
  import Gateway._
  override def receive: Receive = {
    case Shapes(s, p) => s match {
      case Rectangle(px, py, w, h) if p(px, py, w, h) =>
        sender() ! s"Rectangle ($px,$py) width=$w height=$h"
      case Square(px, py, w) if p(px, py, w, w) =>
        sender() ! s"Square ($px,$py) size=$w"
      case Ellipse(px, py, hd, vd) if p(px, py, hd, vd) =>
        sender() ! s"Ellipse ($px,$py) diameterH = $hd diameterV = $vd"
      case Circle(px, py, d) if p(px, py, d, d) =>
        sender() ! s"Circle ($px,$py) size=$d"
      case TextBox(px, py, w, h, text) if p(px, py, w, h) =>
        sender() ! s"Textbox($px,$py) width=$w height=$h ${text.map(x => s"text=$x").getOrElse("")}"
      case _ =>
        sender() ! WidgetTypeEnum.Abort.name
    }
  }
}
