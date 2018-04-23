![The master computer from the Liberator](/zen.jpg)

Zen is a Scala Macro that provides some additional type information when you need it.

Zen is inspired by [macrocosm](https://github.com/retronym/macrocosm) and [Blakes 7](https://en.wikipedia.org/wiki/Characters_of_Blake%27s_7#Zen).

## Showing the type of an Expression

```{.scala .scrollx}
zen.t { List(1,2,3,4).map(_.toString).mkString }
```

which results in:

```{.terminal .scrollx}
type: String
```

You can also name a given type:

```{.scala .scrollx}
zen.t("pair", { 1 -> "one" } )
```

which results in:

```{.terminal .scrollx}
type "pair": (Int, String)
```


## Explaining (desugaring) an Expression

```{.scala .scrollx}
z.explain {
    Option(1) match {
        case Some(x) => true
        case None => false
    }
}
```

results in:

```{.terminal .scrollx}
code: scala.Option.apply[Int](1) match {
  case scala.Some((x @ _)) => true
  case scala.None => false
}
```

## Showing the AST for an Expression

```{.scala .scrollx}
zen.ast { 1 :: 2 :: 3 :: Nil }
```

results in:

```{.terminal .scrollx}
ast: Apply(TypeApply(Select(Apply(Apply(TypeApply(Select(Apply(TypeApply(Select(Select(Select(Select(Ident(scala), scala.collection), scala.collection.immutable), scala.collection.immutable.List), TermName("apply")), List(TypeTree())), List(Literal(Constant(1)), Literal(Constant(2)), Literal(Constant(3)))), TermName("flatMap")), List(TypeTree(), TypeTree())), List(Function(List(ValDef(Modifiers(PARAM), TermName("l1"), TypeTree(), EmptyTree)), Apply(Apply(TypeApply(Select(Apply(TypeApply(Select(Select(Select(Select(Ident(scala), scala.collection), scala.collection.immutable), scala.collection.immutable.List), TermName("apply")), List(TypeTree())), List(Literal(Constant(4)), Literal(Constant(5)), Literal(Constant(6)))), TermName("map")), List(TypeTree(), TypeTree())), List(Function(List(ValDef(Modifiers(PARAM), TermName("l2"), TypeTree(), EmptyTree)), Block(List(ValDef(Modifiers(SYNTHETIC | ARTIFACT), TermName("x$5"), TypeTree(), Ident(TermName("l1")))), Apply(TypeApply(Select(Block(List(ValDef(Modifiers(SYNTHETIC | ARTIFACT), TermName("x$4"), TypeTree(), Ident(TermName("l2")))), Apply(TypeApply(Select(Select(Select(Select(Ident(scala), scala.collection), scala.collection.immutable), scala.collection.immutable.Nil), TermName("$colon$colon")), List(TypeTree())), List(Ident(TermName("x$4"))))), TermName("$colon$colon")), List(TypeTree())), List(Ident(TermName("x$5")))))))), List(TypeApply(Select(Select(This(TypeName("immutable")), scala.collection.immutable.List), TermName("canBuildFrom")), List(TypeTree()))))))), List(TypeApply(Select(Select(This(TypeName("immutable")), scala.collection.immutable.List), TermName("canBuildFrom")), List(TypeTree())))), TermName("flatten")), List(TypeTree().setOriginal(Select(Ident(scala), scala.Int)))), List(TypeApply(Select(Select(Ident(scala), scala.Predef), TermName("$conforms")), List(TypeTree()))))
```

## Showing Everything (Type, Explain and AST)

```{.scala .scrollx}
val source = zen.inspect(Observable.interval(1.second)
  .filter(_ % 2 == 0)
  .flatMap(x => Observable(x, x)))
  . take(10)
```

results in:

```{.terminal .scrollx}
code: monix.reactive.Observable.interval(scala.concurrent.duration.`package`.DurationInt(1).second).filter(((x$2: Long) => x$2.%(2).==(0))).flatMap[Long](((x: Long) => monix.reactive.Observable.apply[Long](x, x)))
ast: Apply(TypeApply(Select(Apply(Select(Apply(Select(Select(Select(Ident(monix), monix.reactive), monix.reactive.Observable), TermName("interval")), List(Select(Apply(Select(Select(Select(Select(Ident(scala), scala.concurrent), scala.concurrent.duration), scala.concurrent.duration.package), TermName("DurationInt")), List(Literal(Constant(1)))), TermName("second")))), TermName("filter")), List(Function(List(ValDef(Modifiers(PARAM | SYNTHETIC), TermName("x$2"), TypeTree(), EmptyTree)), Apply(Select(Apply(Select(Ident(TermName("x$2")), TermName("$percent")), List(Literal(Constant(2)))), TermName("$eq$eq")), List(Literal(Constant(0))))))), TermName("flatMap")), List(TypeTree())), List(Function(List(ValDef(Modifiers(PARAM), TermName("x"), TypeTree(), EmptyTree)), Apply(TypeApply(Select(Select(Select(Ident(monix), monix.reactive), monix.reactive.Observable), TermName("apply")), List(TypeTree())), List(Ident(TermName("x")), Ident(TermName("x")))))))
type: monix.reactive.Observable[Long]
```

## Installation

### Bintray

Add the following to your `build.sbt`:

```{.scala .scrollx}
libraryDependencies += "net.ssanj" %% "zen" % "2.0.0"
```


### Local

Clone this repo and install it locally with:

```{.scala .scrollx}
sbt publishLocal
```

You can then include it in any SBT project as per usual:

```{.scala .scrollx}
libraryDependencies += "net.ssanj" %% "zen" % "2.0.0"
```

## Publishing

To publish a new version perform the following tasks:

```
zen/publish
zen/bintrayRelease
```