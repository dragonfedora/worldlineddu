package assignment1.problem2

object FunctionPredicate {
  val isFour: (Int, Int, Int) => Boolean = (nv: Int, np: Int, nt: Int) => nv == 2 && np == 1 && nt == 0
  val isFullHouse: (Int, Int, Int) => Boolean = (nv: Int, np: Int, nt: Int) => nv == 2 && np == 1 && nt == 1
  val ifEqualTo4: Int => Boolean = (e: Int) => e == 4
  val ifEqualTo2: Int => Boolean = (e: Int) => e == 2
  val ifEqualTo3: Int => Boolean = (e: Int) => e == 3
  val ifEqualTo1: Int => Boolean = (e: Int) => e == 1
  val isGT0: Int => Boolean = (e: Int) => e > 0
}
