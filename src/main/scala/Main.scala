object Main {
  import Interpreter._
  import Lexer._
  import Parser._

  def main(args: Array[String]): Unit = {
    println("parser for lambda calculus")
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