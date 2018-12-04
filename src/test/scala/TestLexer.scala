import org.junit.Test
import org.junit.Assert._

class TestLexer {
  import Lexer._
  import Token._

  @Test
  def TestTokenize: Unit = {
    assertEquals(
      tokenize(" ~  name".toList), 
      (List(Lambda, Lcid("name"), Eof), Nil)
    )

    assertEquals(
      tokenize("~x.x".toList), 
      (List(Lambda, Lcid("x"), Dot, Lcid("x"), Eof), Nil)
    )
  }

  @Test
  def TestNextToken: Unit = {
    assertEquals(nextToken("".toList), (Eof, Nil))
    assertEquals(nextToken("(".toList), (LParen, Nil))
    assertEquals(nextToken(" )txt".toList), (RParen, "txt".toList))
    assertEquals(nextToken("\t~txt".toList), (Lambda, "txt".toList))
    assertEquals(nextToken("\n.".toList), (Dot, Nil))
    assertEquals(nextToken(" x".toList), (Lcid("x"), Nil))
    assertEquals(nextToken("name".toList), (Lcid("name"), Nil))
  }

  @Test
  def TestIgnoreSpaces: Unit = {
    assertEquals(ignoreSpaces("".toList), ('\0', Nil))
    assertEquals(ignoreSpaces(" x".toList), ('x', Nil))
    assertEquals(ignoreSpaces(" \t\nxy".toList), ('x', "y".toList))
  }

  @Test
  def TestNextChar: Unit = {
    assertEquals(nextChar("".toList), ('\0', Nil))
    assertEquals(nextChar("h".toList), ('h', Nil))
    assertEquals(nextChar("(x)".toList), ('(', "x)".toList))
  }
}