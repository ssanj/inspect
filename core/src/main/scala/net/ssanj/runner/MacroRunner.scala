package net.ssanj.runner

object MacroRunner {
  def main(args: Array[String]): Unit = {
    zen.t(List(1,2,3,4))
    zen.t(List(1,2,3,4).map(_.toString).mkString)
    zen.t("pair", { 1 -> "one" })

    val x =
      zen.explain(for {
        one <- Option(1)
        two <- Option(2)
        if (one > 5)
      } yield (one + two))


    zen.explain { Option(1) match {
        case Some(x) => true
        case None => false
      }
    }
  }

  zen.inspect { Right(1) }

  case class Age(value: Int)
  case class Name(value: String)
  case class Person(title: String, name: Name, age: Age, address: Address)
  case class Address(street: Street)
  case class No(value: Int)
  case class StreetName(value: String)
  case class Street(no: No, name: StreetName)
  class Dog(name: String)

  val p1 = Person("mr", Name("Bob"), Age(31), Address(Street(No(10), StreetName("Midtown"))))

  val _ = zen.structure(p1)
  ()
}