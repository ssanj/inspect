package zen

import org.scalatest.{Matchers, WordSpecLike}

final class ZenSpec extends Matchers with WordSpecLike {
  "zen" should {
    "show type" in {
        zen.t { "Test" }
        zen.t("n1", { 123 })
    }

    "show ast" in {
      zen.ast { 1 :: 2 :: 3 :: Nil }
    }

    "explain" in {
      zen.explain { """\d+""".r }
    }

    "inspect" in {
      zen.inspect {
        (for {
                  l1 <- List(1,2,3)
                  l2 <- List(4,5,6)
         } yield (l1 :: l2 :: Nil)).flatten[Int]
      }
    }

    "structure" in {
      case class Age(value: Int)
      case class Name(value: String)
      case class Person(title: String, name: Name, age: Age, address: Address)
      case class Address(street: Street)
      case class No(value: Int)
      case class StreetName(value: String)
      case class Street(no: No, name: StreetName)
      class Dog(name: String)

      val p1 = Person("mr", Name("Bob"), Age(31), Address(Street(No(10), StreetName("Midtown"))))

      zen.structure(p1)
    }

  }
}

