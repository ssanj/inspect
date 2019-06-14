import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import zen.Console.console

package object zen {

  def t[A](value: A): A = macro typeMacro[A]

  def t[A](name: String, value: A): A = macro namedTypeMacro[A]

  def explain[A](value: A): A = macro explainMacro[A]

  def ast[A](value: A): A = macro astMacro[A]

  def inspect[A](value: A): A = macro inspectMacro[A]

  def structure[A](value: A): A = macro Structure.structureMacro[A]

  def typeMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    val tree = c.typecheck(value.tree)
    console(Console.Type, tree.tpe.toString )
    value
  }

  def namedTypeMacro[A: c.WeakTypeTag](c: Context)(name: c.Expr[String], value: c.Expr[A]): c.Expr[A] = {
    val tree = c.typecheck(value.tree)
    console(Console.NamedType(name.tree.toString), tree.tpe.toString)
    value
  }

  def explainMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    console(Console.Explain, showCode(value.tree))
    value
  }

  def astMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

      console(Console.AST, showRaw(value.tree))
    value
  }

  def inspectMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    explainMacro[A](c)(value)
    astMacro[A](c)(value)
    typeMacro[A](c)(value)
    value
  }
}