package assignment2

import org.slf4j.{Logger, LoggerFactory}

object Validators {
  private val logger: Logger = LoggerFactory.getLogger(this.getClass)
  val validate = (px: Int, py: Int, w: Int, h: Int) => {
    val isValidated = px > 0 && py > 0 && w > 0 && h > 0 && px + w <= 1000 && py + h <= 1000
    if (!isValidated)
      logger.debug(s"Validation failed with px=$px, py=$py, w=$w, h=$h, area-x-position=${px + w}, area-y-position=${py + w}")
    isValidated
  }

  val validateEllipse = (px: Int, py: Int, hd: Int, vd: Int) => {
    val isValidated = (px > 0 && py > 0 && hd > 0 && vd > 0) && (px + (hd / 2) <= 1000) && (py + (vd / 2) <= 1000) && (px - (hd / 2) > 0) && (py - (vd / 2) > 0)
    if (!isValidated)
      logger.debug(s"Validation for Ellipse is failed with px=$px, py=$py, hd=$hd, vd=$vd")
    isValidated
  }


  val validateCircle = (px: Int, py: Int, d: Int, r: Int) => {
    val isValidated = (px > 0 && py > 0 && d > 0) && (px + (d / 2) <= 1000) && (py + (d / 2) <= 1000) && (px - (d / 2) > 0) && (py - (d / 2) > 0)
    if (!isValidated)
      logger.debug(s"Validation for circle is failed with px=$px, py=$py, d=$d")
    isValidated
  }

}
