object Lexer {
  import Token._

  def alphabets = 'a' to 'z' toList
  def whitespaces = List(' ', '\t', '\n')

  def nextChar(ls: List[Char]): (Char, List[Char]) =
    ls match {
      case Nil => ('\0', Nil)
      case h :: t => (h, t)
    }

  @annotation.tailrec
  def ignoreSpaces(ls: List[Char]): (Char, List[Char]) =
    nextChar(ls) match {
      case (h, t)  if (whitespaces.contains(h)) => ignoreSpaces(t)
      case (h, t) => (h, t)
    }

  def nextToken(ls: List[Char]): (Token, List[Char]) =
    ignoreSpaces(ls) match {
      case ('\0', t) => (Eof, t)
      case ('(', t) => (LParen, t)
      case (')', t) => (RParen, t)
      case ('.', t) => (Dot, t)
      case ('~', t) => (Lambda, t)
      case (h, t) if (alphabets.contains(h)) => {
        @annotation.tailrec
        def loop(acc: String, ls: List[Char]): (Token, List[Char]) = {
          val (h, t) = nextChar(ls)
          if (alphabets.contains(h)) loop(acc + h, t)
          else (Lcid(acc), ls)
        }
        loop(s"$h", t)
      }
      case (h, _) => sys.error(s"Unexpected token $h")
    }

  def tokenize(ls: List[Char]): (List[Token], List[Char]) = {
    @annotation.tailrec
    def loop(acc: List[Token], ls: List[Char]): (List[Token], List[Char]) =
      nextToken(ls) match {
        case (Eof, t) => (Eof :: acc reverse, t)
        case (h, t) => loop(h :: acc, t)
      }
    loop(Nil, ls)
  }
    
}