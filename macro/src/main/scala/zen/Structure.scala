package zen

import scala.reflect.macros.blackbox.Context
import Console.console

object Structure {
  final case class Field(name: String, typeName: String, nested: Option[CaseClass])
  final case class CaseClass(name: String, fields: Seq[Field])

  def structureMacro[A: c.WeakTypeTag](c: Context)(value: c.Expr[A]): c.Expr[A] = {
    import c.universe._

    def isCaseClass(tpe: Type): Boolean = tpe.typeSymbol.asClass.isCaseClass

    def getClassName(tpe: Type): String = tpe.typeSymbol.asClass.name.toString

    def getMethodName(m: MethodSymbol): String = m.name.toString

    val treeType = c.typecheck(value.tree).tpe

    def getCaseClassMethods(tpe: Type): Option[CaseClass] = {
      if (isCaseClass(tpe)) {
        val fields: List[Field] = tpe.decls.collect {
          case m: MethodSymbol if m.isCaseAccessor =>
            val returnType = m.returnType
            Field(getMethodName(m),
                  getClassName(returnType),
                  if (isCaseClass(returnType)) getCaseClassMethods(returnType)
                  else None
            )
        }.toList

        Option(CaseClass(getClassName(tpe), fields))
      } else None
    }

    getCaseClassMethods(treeType).foreach(cc => console(Console.Structure, separateAndShow(cc)))

    value
  }

  private def separateCaseClasses(caseClass: CaseClass): Seq[CaseClass] = {
      val nestedCaseClasses = caseClass.fields.collect {
        case Field(_, _, Some(cc2@CaseClass(_, _))) => cc2
      }.toList

      caseClass +: nestedCaseClasses.flatMap(separateCaseClasses)
  }

  private def separateAndShow = showCaseClasses _ compose separateCaseClasses _

  private def showCaseClasses(caseClasses: Seq[CaseClass]): String = caseClasses.map(showCaseClass).mkString("\n")

  private def showCaseClass(caseClass: CaseClass): String = s"${caseClass.name}(${showFields(caseClass.fields)})"

  private def showFields(fields: Seq[Field]): String = fields.map(showField).mkString(", ")

  private def showField(field: Field): String = s"${field.name}:${field.typeName}"
}