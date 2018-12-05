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
  }
}



trait AST
case class Abstraction(val head: String, val body: AST) extends AST {
  override def toString = s"(\\${head}.${body})"
}
case class Identifier(val name: String, val index: Int) extends AST {
  override def toString = s"$name"
}
case class Application(val lhs: AST, val rhs: AST) extends AST {
  override def toString = s"${lhs}${rhs}"
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