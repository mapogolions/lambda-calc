object Parser {
  import Token._

  def parse(tokens: List[Token]): AST = {
    val (ast, ts) = term(Nil, tokens)
    ts match {
      case Eof :: Nil => ast
      case _ => sys.error(s"${ts}. Parser.parse")
    }
  }

  def term(ctx: List[String], tokens: List[Token]): (AST, List[Token]) = {
    tokens match {
      case Lambda :: Lcid(name) :: Dot :: t => {
        val (ast, ts) = term(name :: ctx, t)
        (Abstraction(name, ast), ts)
      }
      case _ => application(ctx, tokens)
    }
  }

  def application(ctx: List[String], tokens: List[Token]): (AST, List[Token]) = {
    @annotation.tailrec
    def loop(acc: AST, ctx: List[String], tokens: List[Token]): (AST, List[Token]) =
      atom(ctx, tokens) match {
        case (FAKE, t) => (acc, t)
        case (ast, t) => loop(Application(acc, ast), ctx, t)
      }

    val (lhs, ts1) = atom(ctx, tokens)
    val (rhs, ts2) = atom(ctx, ts1)
    rhs match {
      case FAKE => (lhs, ts1)
      case _ => loop(Application(lhs, rhs), ctx, ts2)
    }
  }


  def atom(ctx: List[String], tokens: List[Token]): (AST, List[Token]) =
    tokens match {
      case LParen :: t => {
        val (ast, ts) = term(ctx, t)
        ts match {
          case RParen :: t => (ast, t)
          case _ => sys.error(s"Should be RParen")
        }
      }
      case Lcid(name) :: t => ctx.indexOf(name) match {
        case -1 => (Identifier(name, -1), t)
        case i => (Identifier(ctx(i), i), t)
      }
      // case Lcid(name) :: t => (Identifier(ctx.find(name)), ctx.indexOf(name)), t)
      case _ => (FAKE, tokens)
    }

}