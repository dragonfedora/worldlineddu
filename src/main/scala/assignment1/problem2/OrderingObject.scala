package assignment1.problem2

import assignment1.problem2.Cons._

object OrderingObject {
  implicit object ValueOrdering extends Ordering[Card] {
    def compare(a: Card, b: Card): Int =
      values.indexOf(b.value) - values.indexOf(a.value)
  }

  object HandOrdering extends Ordering[Hand] {
    def compare(h1: Hand, h2: Hand): Int = h1.compareTo(h2)
  }
}
