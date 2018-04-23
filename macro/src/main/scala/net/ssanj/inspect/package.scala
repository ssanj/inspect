package net.ssanj

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.io.AnsiColor._

object inspect {

  sealed trait MacroType
  case object ShowT extends MacroType
  final case class ShowTN(name: String) extends MacroType
  case object ShowA extends MacroType
  case object Explain extends MacroType

  def showT[A](value: A): A = macro showTMacro[A]

  def showTN[A](name: String, value: A): A = macro showTNMacro[A]

  def explain[A](value: A): A = macro explainMacro[A]

  def showA[A](value: A): A = macro showAMacro[A]

  def inspect[A](value: A): A = macro inspectMacro[A]

  def showTMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    val tree = c.typecheck(value.tree)
    console(ShowT, tree.tpe.toString )
    value
  }

  def showTNMacro[A: c.WeakTypeTag](c: Context)(name: c.Expr[String], value: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    val tree = c.typecheck(value.tree)
    console(ShowTN(name.tree.toString), tree.tpe.toString )
    value
  }

  def inspectMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    explainMacro[A](c)(value)
    showAMacro[A](c)(value)
    showTMacro[A](c)(value)
    value
  }

  def showAMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    console(ShowA, showRaw(value.tree))
    value
  }

  def explainMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    console(Explain, showCode(value.tree))
    value
  }

  private def console(macroType: MacroType, value: String): Unit = {
    val (prefix, colour) = macroType match {
      case ShowT        => ("type", GREEN)
      case ShowTN(name) => (s"type $name", GREEN)
      case ShowA        => ("ast", CYAN)
      case Explain      => ("code", YELLOW)
    }

    println(s"${colour}${prefix}${RESET}: ${value}")
  }
}
