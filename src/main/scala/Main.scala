object Main {
  import Interpreter._
  import Lexer._
  import Parser._

  def main(args: Array[String]): Unit = {
    println("parser for lambda calculus") 
    /* val res = eval(parse(tokenize("(~f. ~x. f x)(~y. y)".toList)._1)) // check
    println(res)
    val res2 = eval(parse(tokenize("(~x. x)(~y. y y)(~z. z)".toList)._1))
    println(res2) */
    // val res3 = eval(parse(tokenize("(~f. ~a. ~b. f a b)".toList)._1))
    // println(res3)
    
    // &&
    val TrueAndTrue = eval(parse(tokenize("(~p. ~q. p q p)(~a. ~b. a)(~a. ~b. a)".toList)._1))
    println(TrueAndTrue) 
    val FalseAndTrue = eval(parse(tokenize("(~p. ~q. p q p)(~a. ~b. a)(~a. ~b. b)".toList)._1))
    println(FalseAndTrue)
    val TrueAndFalse = eval(parse(tokenize("(~p. ~q. p q p)(~a. ~b. b)(~a. ~b. a)".toList)._1))
    println(TrueAndFalse)
    val FalseAndFalse = eval(parse(tokenize("(~p. ~q. p q p)(~a. ~b. b)(~a. ~b. b)".toList)._1))
    println(FalseAndFalse)

    println()
    // `Or`
    val TrueOrTrue = eval(parse(tokenize("(~p. ~q. p p q)(~a. ~b. a)(~a. ~b. a)".toList)._1))
    println(TrueOrTrue) 
    val FalseOrTrue = eval(parse(tokenize("(~p. ~q. p p q)(~a. ~b. a)(~a. ~b. b)".toList)._1))
    println(FalseOrTrue)
    val TrueOrFalse = eval(parse(tokenize("(~p. ~q. p p q)(~a. ~b. b)(~a. ~b. a)".toList)._1))
    println(TrueOrFalse)
    val FalseOrFalse = eval(parse(tokenize("(~p. ~q. p p q)(~a. ~b. b)(~a. ~b. b)".toList)._1))
    println(FalseOrFalse)
    // `NOT`
    val NotFalse = eval(parse(tokenize("(~p. p (~a. ~b. b)(~a. ~b. a))(~a. ~b. b)".toList)._1))
    println(s"Not false = $NotFalse")
    val NotTrue = eval(parse(tokenize("(~p. p (~a. ~b. b)(~a. ~b. a))(~a. ~b. a)".toList)._1))
    println(s"Not true = $NotTrue")

    println()

    // `if ... else ...`
    val TrueIf = eval(parse(tokenize("(~p. ~a. ~b. p a b)(~a. ~b. a)(~x.x)(~y.y)".toList)._1))
    println(TrueIf)
    val FalseIf = eval(parse(tokenize("(~p. ~a. ~b. p a b)(~a. ~b. b)(~x.x)(~y.y)".toList)._1))
    println(FalseIf)

    println()

    // Predicats
    val ZeroIsZero = 
      eval(parse(tokenize("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. x)".toList)._1))
    println(ZeroIsZero) // true

    val OneIsZero = 
      eval(parse(tokenize("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. f x)".toList)._1))
    println(OneIsZero) // false

    val TwoIsZero = 
      eval(parse(tokenize("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. f (f x))".toList)._1))
    println(TwoIsZero) // false
    /* val FiveIsZero = 
      eval(parse(tokenize("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. f(f(f(f(f x)))))".toList)._1))
    println(FiveIsZero) // false */
    exec("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. f(f(f(f(f x)))))")
    exec("(~x.x)(~y.yy)")
    /* val andOne = 
      eval(parse(tokenize("(~n. ~f. ~x. f (n f x))(~f. ~x. f x)".toList)._1))
    println(andOne) */
  }

}



trait AST
case class Abstraction(val head: String, val body: AST) extends AST {
  override def toString = s"(\\${head}. ${body})"
}
case class Identifier(val name: String, val index: Int) extends AST {
  override def toString = s"$name"
}
case class Application(val lhs: AST, val rhs: AST) extends AST {
  override def toString = s"${lhs} ${rhs}"
}
case object FAKE extends AST



enum Token {
  case LParen
  case RParen
  case Lambda
  case Dot
  case Eof
  case Lcid(val name: String)
}