package assignment2

import assignment2.Validators._
import org.scalatest.freespec.AnyFreeSpec

class ValidatorsSpecs extends AnyFreeSpec {

  "Validate rectangle, text-box, square" - {
    "should validate true x=100, y=100, w=10, h=10" in {
      assert(validate(100, 100, 10, 10))
    }
    "should validate false x=100, y=10000, w=10, h=10 as Y size > canvas size" in {
      assert(!validate(100, 10000, 10, 10))
    }
    "should validate false x=100, y=1000, w=600, h=10 as y draw size > canvas size" in {
      assert(!validate(100, 1000, 600, 10))
    }
  }

  "Validate Ellipse" - {
    "should validate true x=100, y=100, hd=10, vd=10" in {
      assert(validateEllipse(100, 100, 10, 10))
    }
    "should validate false x=100, y=10000, hd=10, vd=10 as Y size > canvas size" in {
      assert(!validateEllipse(100, 10000, 10, 10))
    }
    "should validate false x=100, y=1000, hd=600, vd=10 as y draw size > canvas size" in {
      assert(!validateEllipse(100, 1000, 600, 10))
    }
    "should validate false x=10, y=1000, hd=600, vd=10 as x draw size < canvas size" in {
      assert(!validateEllipse(100, 1000, 600, 10))
    }
  }

  "Validate Circle" - {
    "should validate true x=100, y=100, hd=10" in {
      assert(validateEllipse(100, 100, 10, 10))
    }
    "should validate false x=100, y=10000, hd=10, vd=10 as Y size > canvas size" in {
      assert(!validateEllipse(100, 10000, 10, 10))
    }

    "should validate false x=1000, y=100, hd=10, vd=10 as X size > canvas size" in {
      assert(!validateEllipse(1000, 1000, 10, 10))
    }
    "should validate false x=100, y=1000, hd=600, vd=10 as y draw size > canvas size" in {
      assert(!validateEllipse(100, 1000, 600, 10))
    }
    "should validate false x=10, y=1000, hd=600, vd=10 as x draw size < canvas size" in {
      assert(!validateEllipse(100, 1000, 600, 10))
    }
  }

}
