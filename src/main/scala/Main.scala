object Main {
  def main(args: Array[String]): Unit = {
    println("parser for lambda calculus")
  }
}



trait AST
case class Abstraction(val head: String, val body: AST) extends AST
case class Identifier(val value: String) extends AST
case class Application(val lhs: AST, val rhs: AST) extends AST
case object FAKE extends AST



enum Token {
  case LParen
  case RParen
  case Lambda
  case Dot
  case Eof
  case Lcid(val name: String)
}