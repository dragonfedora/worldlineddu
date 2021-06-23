package assignment2

import akka.actor.{Actor, ActorLogging, Props, Stash}
import assignment2.WidgetTypeEnum.ResponseFormat

object Gateway {
  case class Shapes(shape: Shape, p: (Int, Int, Int, Int) => Boolean)
  case class Response()
  case class Request(sType: String, x: Int, y: Int
                     , w: Option[Int] = None, h: Option[Int] = None, hd: Option[Int] = None, vd: Option[Int] = None
                     , d: Option[Int] = None, text: Option[String] = None)
}

class Gateway extends Actor with ActorLogging with Stash{
  import Gateway._
  import Validators._
  import Widgets._

  val widgets = context.actorOf(Props[Widgets], "widgets")

  override def receive: Receive =  process()

  def process(list: List[String] = List()): Receive = {
    case Request(t, x, y, w, h, hd, vd, d, text) =>

      t match {
        case WidgetTypeEnum.Rectangle.name =>
          val width = w.getOrElse(0)
          val height = h.getOrElse(0)
          log.info(s"Request received ${WidgetTypeEnum.Rectangle.name} with x-position=$x, y-position=$y, width=$width, height=$height")
          widgets ! Shapes(Rectangle(x, y, width, height), validate)
        case WidgetTypeEnum.Square.name =>
          val width = w.getOrElse(0)
          log.info(s"Request received ${WidgetTypeEnum.Square.name} with x-position=$x, y-position=$y, width=$width")
          widgets ! Shapes(Square(x, y, width), validate)
        case WidgetTypeEnum.Ellipse.name =>
          val hDiameter = hd.getOrElse(0)
          val vDiameter = vd.getOrElse(0)
          log.info(s"Request received ${WidgetTypeEnum.Ellipse.name} with x-position=$x, y-position=$y, horizontal-diameter=$hDiameter, vertical-diameter=$vDiameter")
          widgets ! Shapes(Ellipse(x, y, hd.getOrElse(0), vd.getOrElse(0)), validateEllipse)
        case WidgetTypeEnum.Circle.name =>
          val diameter = d.getOrElse(0)
          log.info(s"Request received ${WidgetTypeEnum.Circle.name} with x-position=$x, y-position=$y, diameter=$diameter")
          widgets ! Shapes(Circle(x, y, d.getOrElse(0)), validateCircle)
        case WidgetTypeEnum.Textbox.name =>
          val width = w.getOrElse(0)
          val height = h.getOrElse(0)
          log.info(s"Request received ${WidgetTypeEnum.Textbox.name} with x-position=$x, y-position=$y, width=$width, height=$height")
          widgets ! Shapes(TextBox(x, y, w.getOrElse(0), h.getOrElse(0), text), validate)
        case _ =>
      }
    case widgetReply: String =>
      context.become(process(list :+ widgetReply))
    case Response =>
      var temp = ""
      list.foreach(s => temp += s"$s\n")
      sender().tell(if (list.isEmpty || list.contains(WidgetTypeEnum.Abort.name)) WidgetTypeEnum.Abort.name else ResponseFormat.name.format(temp), sender)
  }
}
