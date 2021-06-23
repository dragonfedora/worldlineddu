package assignment1.problem3

import org.scalatest.flatspec.AnyFlatSpec

class TriangleProblemSpecs extends AnyFlatSpec{

  it should "max sum in triangle" in {
    assert(TriangleProblem.process(Some("triangle.txt")) == 7273)
    assert(TriangleProblem.process(Some("triangle1.txt")) == 23)
  }
}
