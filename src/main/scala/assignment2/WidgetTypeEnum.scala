package assignment2

sealed class WidgetType(val name: String)

object WidgetTypeEnum extends Enumeration {
  case object Rectangle extends WidgetType("rectangle")

  case object Square extends WidgetType("square")

  case object Ellipse extends WidgetType("ellipse")

  case object Circle extends WidgetType("circle")

  case object Textbox extends WidgetType("textbox")

  case object Abort extends WidgetType("+++++Abort+++++")

  case object ResponseFormat extends WidgetType(
    s"""
       |----------------------------------------------------------------------------

                                          Bill of Materials

       |-----------------------------------------------------------------------------
       |%s

       |-----------------------------------------------------------------------------
                    """.stripMargin)
}
