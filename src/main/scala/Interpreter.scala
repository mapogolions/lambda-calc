object Interpreter {
  import Token._
  import Parser._
  import Lexer._
  
  def exec(source: String) = (ast andThen eval andThen println)(source)
  
  private def ast(source: String) = {
    val (tokens, _) = tokenize(source.toList)
    eval(parse(tokens))
  }

  def eval(ast: AST): AST = ast match {
    case Application(lhs, rhs) => 
      (lhs, rhs) match {
        case (Abstraction(hd1, bd1), Abstraction(hd2, bd2)) => eval(substitute(rhs, bd1))
        case (Abstraction(hd, bd), _) => eval(Application(lhs, eval(rhs)))
        case (x, Abstraction(hd, bd)) => eval(Application(eval(lhs), rhs))
      }
    case _ => ast
  } 

  def traverse(f: Int => Config) = {
    def fun(node: AST, by: Int): AST = {
      val config = f(by)
      node match {
        case Application(lhs, rhs) => config.App(Application(lhs, rhs))
        case Abstraction(head, body) => config.Abs(Abstraction(head, body))
        case Identifier(name, index) => config.Id(Identifier(name, index))
      }
    }
    fun
  }

  def shift(by: Int, node: AST): AST = {
    def aux(node: AST, by: Int): AST = traverse((from: Int) => {
      case object conf extends Config {
        def App(app: Application): AST = 
          Application(aux(app.lhs, from), aux(app.rhs, from))
        def Abs(abs: Abstraction): AST = 
          Abstraction(abs.head, aux(abs.body, from + 1))
        def Id(obj: Identifier): AST = 
          Identifier(obj.name, obj.index + (if (obj.index >= from) by  else 0))
      }
      conf
    })(node, by)
    aux(node, 0)
  }

  def subst(value: AST, node: AST): AST = {
    def aux(node: AST, by: Int): AST = traverse((depth: Int) => {
      case object conf extends Config {
        def App(app: Application): AST = 
          Application(aux(app.lhs, depth), aux(app.rhs, depth))
        def Abs(abs: Abstraction): AST = 
          Abstraction(abs.head, aux(abs.body, depth + 1))
        def Id(obj: Identifier): AST = 
          if (depth == obj.index) shift(depth, value) else obj
      }
      conf
    })(node, by)
    aux(node, 0)
  }

  def substitute(value: AST, node: AST): AST = shift(-1, subst(shift(1, value), node))
}

trait Config {
  def App(node: Application): AST
  def Abs(node: Abstraction): AST
  def Id(node: Identifier): AST
}