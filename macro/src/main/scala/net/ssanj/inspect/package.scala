package net.ssanj

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.io.AnsiColor._

object inspect {

  def showT[A](value: A): A = macro showTMacro[A]

  def explain[A](value: A): A = macro explainMacro[A]

  def showA[A](value: A): A = macro showAMacro[A]

  def inspect[A](value: A): A = macro inspectMacro[A]

  def showTMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    val tree = c.typecheck(value.tree)
    console("type", tree.tpe.toString )
    value
  }

  def inspectMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    console("code", showCode(value.tree))
    console("ast", showRaw(value.tree))
    val tree = c.typecheck(value.tree)
    console("type", tree.tpe.toString)
    value
  }

  def showAMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    console("ast", showRaw(value.tree))
    value
  }

  def explainMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    console("code", showCode(value.tree))
    value
  }

  private def console(prefix: String, value: String): Unit = {
    println(s"${GREEN}${prefix}${RESET}: ${value}")
  }
}
