import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.io.AnsiColor._

package object zen {

  sealed trait MacroType
  case object Type extends MacroType
  final case class NamedType(name: String) extends MacroType
  case object AST extends MacroType
  case object Explain extends MacroType

  def t[A](value: A): A = macro typeMacro[A]

  def t[A](name: String, value: A): A = macro namedTypeMacro[A]

  def explain[A](value: A): A = macro explainMacro[A]

  def ast[A](value: A): A = macro astMacro[A]

  def inspect[A](value: A): A = macro inspectMacro[A]

  def unwrap[A](value: A): A = macro Unwrap.unwrapMacro[A]

  def typeMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    val tree = c.typecheck(value.tree)
    console(Type, tree.tpe.toString )
    value
  }

  def namedTypeMacro[A: c.WeakTypeTag](c: Context)(name: c.Expr[String], value: c.Expr[A]): c.Expr[A] = {
    val tree = c.typecheck(value.tree)
    console(NamedType(name.tree.toString), tree.tpe.toString)
    value
  }

  def explainMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    console(Explain, showCode(value.tree))
    value
  }

  def astMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

      console(AST, showRaw(value.tree))
    value
  }

  def inspectMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    explainMacro[A](c)(value)
    astMacro[A](c)(value)
    typeMacro[A](c)(value)
    value
  }

  private def console(macroType: MacroType, value: String): Unit = {
    val (prefix, colour) = macroType match {
      case Type            => ("type", GREEN)
      case NamedType(name) => (s"type $name", GREEN)
      case AST             => ("ast", CYAN)
      case Explain         => ("code", YELLOW)
    }

    println(s"${colour}${prefix}${RESET}: ${value}")
  }
}