package assignment1.problem2

import org.scalatest.freespec.AnyFreeSpec

class FunctionPredicateSpecs extends AnyFreeSpec {

  import assignment1.problem2.FunctionPredicate._

  "isFour" - {
    "should return true 2, 1, 0 " in {
      assert(isFour(2, 1, 0))
    }
    "should return false 2, 1, 1 " in {
      assert(!isFour(2, 2, 1))
    }
  }
  "isFullHouse" - {
    "should return true 2, 1, 1 " in {
      assert(isFullHouse(2, 1, 1))
    }
    "should return false 2, 1, 2 " in {
      assert(!isFullHouse(2, 2, 1))
    }
  }
  "ifEqualTo4" - {
    "should return true 4 " in {
      assert(ifEqualTo4(4))
    }
    "should return false 2, 1, 2 " in {
      assert(!ifEqualTo4(3))
    }
  }
  "ifEqualTo2" - {
    "should return true 2 " in {
      assert(ifEqualTo2(2))
    }
    "should return false 3 " in {
      assert(!ifEqualTo2(3))
    }
  }
  "ifEqualTo3" - {
    "should return true 4 " in {
      assert(ifEqualTo3(3))
    }
    "should return false 2 " in {
      assert(!ifEqualTo3(2))
    }
  }
  "ifEqualTo1" - {
    "should return true 1 " in {
      assert(ifEqualTo1(1))
    }
    "should return false 2 " in {
      assert(!ifEqualTo1(2))
    }
  }
  "isGT0" - {
    "should return true 1 " in {
      assert(isGT0(3))
    }
    "should return false 2 " in {
      assert(!isGT0(-1))
    }
  }
}
