import org.junit.Test
import org.junit.Assert._

class TestParser {
  import Parser._
  import Lexer._
  import Token._


  @Test
  def TestTermApplicationAtom: Unit = {
    assertEquals(
      term(Nil, tokenize("(~x. x)(~y. y b)".toList)._1),
      (
        Application(
          Abstraction("x", Identifier("x", 0)), 
          Abstraction("y", Application(Identifier("y", 0), Identifier("b", -1)))
        ),
        List(Eof)
      )
    )

    assertEquals(
      term(Nil, tokenize("(~x. x)(~y. y)".toList)._1),
      (
        Application(Abstraction("x", Identifier("x", 0)), Abstraction("y", Identifier("y", 0))),
        List(Eof)
      )
    )

    assertEquals(
      tokenize("~x. x b".toList),
      (List(Lambda, Lcid("x"), Dot, Lcid("x"), Lcid("b"), Eof), Nil)
    )

    assertEquals(
      term(Nil, tokenize("~x. x b".toList)._1),
      (Abstraction("x", Application(Identifier("x", 0), Identifier("b", -1))), List(Eof))
    )

    assertEquals(
      term(Nil, tokenize("~x. ~y. x".toList)._1),
      (
        Abstraction("x", Abstraction("y", Identifier("x", 1))), 
        List(Eof)
      )
    )

    assertEquals(
      term(Nil, tokenize("((~x. x))".toList)._1),
      (Abstraction("x", Identifier("x", 0)), List(Eof))
    )

    assertEquals(
      term(Nil, tokenize("(~x. x)".toList)._1),
      (Abstraction("x", Identifier("x", 0)), List(Eof))
    )

    assertEquals(
      term(Nil, tokenize("~x.x".toList)._1),
      (Abstraction("x", Identifier("x", 0)), List(Eof))
    )
  }

  @Test
  def TestAtom: Unit = {
    assertEquals(
      atom(Nil, tokenize("~x.x".toList)._1),
      (FAKE, List(Lambda, Lcid("x"), Dot, Lcid("x"), Eof))
    )

    assertEquals(
      atom(Nil, tokenize(".".toList)._1),
      (FAKE, List(Dot, Eof))
    )
    assertEquals(
      atom(List("xy"), tokenize(" \t\nxy".toList)._1),
      (Identifier("xy", 0), List(Eof))
    )
    assertEquals(
      atom(List("name"), tokenize("name".toList)._1),
      (Identifier("name", 0), List(Eof))
    )
    assertEquals(
      atom(List("x"), tokenize("x".toList)._1),
      (Identifier("x", 0), List(Eof))
    )
  }
}