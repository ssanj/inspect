package net.ssanj.inspect

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

object ShowTMacros {

  //zen.t
  def showT[A](value: A): A = macro showTMacro[A]

//zen.x
  def explain[A](value: A): A = macro explainMacro[A]

//zen.ast
  def showA[A](value: A): A = macro showAMacro[A]

//zen.info
  def inspect[A](value: A): A = macro inspectMacro[A]

  def showTMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    val tree = c.typecheck(value.tree)
    println(s"type: ${tree.tpe}")
    value
  }

  def inspectMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    println(s"code: ${showCode(value.tree)}")
    println(s"ast: ${showRaw(value.tree)}")
    val tree = c.typecheck(value.tree)
    println(s"type: ${tree.tpe}")
    value
  }

  def showAMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    println(s"ast: ${showRaw(value.tree)}")
    value
  }

  def explainMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    println(s"code: ${showCode(value.tree)}")
    value
  }
}
