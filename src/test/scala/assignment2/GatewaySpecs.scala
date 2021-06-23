package assignment2

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import assignment2.WidgetTypeEnum.{Abort, ResponseFormat}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class GatewaySpecs extends TestKit(ActorSystem("GatewaySpecs"))
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {
  implicit val timeout: Timeout = 3.seconds
  val RECTANGLE_STR = "Rectangle (%s,%s) width=%s height=%s\n"
  val SQUARE_STR = "Square (%s,%s) size=%s\n"
  val ELLIPSE_STR = "Ellipse (%s,%s) diameterH = %s diameterV = %s\n"
  val CIRCLE_STR = "Circle (%s,%s) size=%s\n"
  val TEXTBOX_STR = "Textbox(%s,%s) width=%s height=%s %s\n"

  override def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  import Gateway._

  "rectangle" should {
    "generate report if valid input" in {
      assert(testRectangle("rectangle", 100, 200, 50, 50) == ResponseFormat.name.format(RECTANGLE_STR.format(100, 200, 50, 50)))
    }
    "abort if invalid input" in {
      assert(testRectangle("rectangle", 100, 1000, 50, 50) == Abort.name)
    }
  }
  "square" should {
    "generate report if valid input" in {
      assert(testRectangle("square", 100, 100, 10, 10) == ResponseFormat.name.format(SQUARE_STR.format(100, 100, 10)))
    }
    "abort if invalid input" in {
      assert(testRectangle("square", 100, 1000, 50, 50) == Abort.name)
    }
  }
  "ellipse" should {
    "generate report if valid input" in {
      assert(testEllipse(100, 100, 10, 10) == ResponseFormat.name.format(ELLIPSE_STR.format(100, 100, 10, 10)))
    }
    "abort if invalid input" in {
      assert(testEllipse(100, 1000, 10, 10) == Abort.name)
    }
  }
  "circle" should {
    "generate report if valid input" in {
      assert(testCircle(100, 100, 10) == ResponseFormat.name.format(CIRCLE_STR.format(100, 100, 10)))
    }
    "abort if invalid input" in {
      assert(testCircle(1000, 100, 10) == Abort.name)
    }
  }
  "text-box" should {
    "generate report if valid input and no text value given" in {
      assert(testRectangle("textbox", 100, 200, 50, 50) == ResponseFormat.name.format(TEXTBOX_STR.format(100, 200, 50, 50, "")))
    }
    "generate report if valid input and text value given" in {
      assert(testRectangle("textbox", 100, 200, 50, 50, Some("this is test")) == ResponseFormat.name.format(TEXTBOX_STR.format(100, 200, 50, 50, "text=this is test")))
    }
    "abort if invalid input" in {
      assert(testRectangle("textbox", 1000, 200, 10, 10, Some("this is test")) == Abort.name)
    }
  }
  "multiple widgets" should {
    "generate report if valid input" in {
      val gateway = system.actorOf(Props[Gateway])
      gateway ! Request("rectangle", 100, 100, Some(50), Some(50))
      gateway ! Request("square", 200, 100, Some(10), Some(10))
      gateway ! Request("ellipse", 400, 150, hd = Some(40), vd = Some(50))
      gateway ! Request("circle", 100, 130, d = Some(20))
      gateway ! Request("textbox", 100, 170, Some(70), Some(50), text = Some("this is multi test"))
      Thread.sleep(100)
      val future = gateway ? Response
      val r = Await.result(future, timeout.duration)
      val cons = s"${RECTANGLE_STR.format(100, 100, 50, 50)}${SQUARE_STR.format(200, 100, 10, 10)}${ELLIPSE_STR.format(400, 150, 40, 50)}${CIRCLE_STR.format(100, 130, 20)}${TEXTBOX_STR.format(100, 170, 70, 50, "text=this is multi test")}"
      assert(r == ResponseFormat.name.format(cons))
    }
    "abort if invalid input for any widget" in {
      val gateway = system.actorOf(Props[Gateway])
      gateway ! Request("rectangle", 1000, 100, Some(50), Some(50))
      gateway ! Request("square", 200, 100, Some(10), Some(10))
      gateway ! Request("ellipse", 400, 150, hd = Some(40), vd = Some(50))
      gateway ! Request("circle", 100, 130, d = Some(20))
      gateway ! Request("textbox", 100, 170, Some(70), Some(50), text = Some("this is multi test"))
      Thread.sleep(100)
      val future = gateway ? Response
      val r = Await.result(future, timeout.duration)
      assert(r == Abort.name)
    }
  }

  def testRectangle(t: String, x: Int, y: Int, w: Int, h: Int, text: Option[String] = None) = {
    val gateway = system.actorOf(Props[Gateway])
    gateway ! Request(t, x, y, Some(w), Some(h), text = text)
    Thread.sleep(100)
    val future = gateway ? Response
    Await.result(future, timeout.duration)
  }

  def testEllipse(x: Int, y: Int, hd: Int, vd: Int) = {
    val gateway = system.actorOf(Props[Gateway])
    gateway ! Request("ellipse", x, y, hd = Some(hd), vd = Some(vd))
    Thread.sleep(100)
    val future = gateway ? Response
    Await.result(future, timeout.duration)
  }

  def testCircle(x: Int, y: Int, d: Int) = {
    val gateway = system.actorOf(Props[Gateway])
    gateway ! Request("circle", x, y, d = Some(d))
    Thread.sleep(100)
    val future = gateway ? Response
    Await.result(future, timeout.duration)
  }

}
