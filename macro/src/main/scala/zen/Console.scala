package zen

import scala.io.AnsiColor._

object Console {
  sealed trait MacroType
  case object Type extends MacroType
  final case class NamedType(name: String) extends MacroType
  case object AST extends MacroType
  case object Explain extends MacroType
  case object Structure extends MacroType

  private[zen] def console(macroType: MacroType, value: String): Unit = {
    val (prefix, colour) = macroType match {
      case Type            => ("type", GREEN)
      case NamedType(name) => (s"type $name", GREEN)
      case AST             => ("ast", CYAN)
      case Explain         => ("code", YELLOW)
      case Structure       => ("structure", RED)
    }

    println(s"${colour}${prefix}${RESET}: ${value}")
  }
}
