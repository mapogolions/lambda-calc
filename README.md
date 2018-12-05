# Interpreter for lambda calculus


### For more information:

[What is lambda calculus](https://en.wikipedia.org/wiki/Lambda_calculus)
[Chuch encoding](https://en.wikipedia.org/wiki/Church_encoding)

Inspired - [A λ-calculus interpreter in less than 300 lines of JavaScript](https://tadeuzagallo.com/blog/writing-a-lambda-calculus-interpreter-in-javascript/)

### How to use

```sh
$ git clone ...
$ cd root of project
$ sbt
stb:λ> test
stb:λ> console
scala> import Interpreter._
scala> exec("(~p. ~q. p q p)(~a. ~b. a)(~a. ~b. b)")
(\a. (\b b))
scala> val True = "(~a. ~b. a)"
val True: String = (~a. ~b. a)
scala> val False = "(~a. ~b. b)"
val False: String = (~a. ~b. b)
scala> val Not = "(~p. p (~a. ~b. b)(~a. ~b. a))"
val Not = (~p. p (~a. ~b. b)(~a. ~b. a))
scala> exec(Not + False)
(\a. (\b. a))
scala> exec(s"${Not}${True}")
(\a. (\b. b))
scala> ...
```
Enter `:quit` for terminated session

### More examples:

```scala
 // &&
exec("(~p. ~q. p q p) (~a. ~b. a) (~a. ~b. a)") // true && true -> true
exec("(~p. ~q. p q p) (~a. ~b. a) (~a. ~b. b)") // true && false -> false
exec("(~p. ~q. p q p) (~a. ~b. b) (~a. ~b. a)") // false && true -> false
exec("(~p. ~q. p q p) (~a. ~b. b) (~a. ~b. b)") // false && false -> false

// `Or`
exec("(~p. ~q. p p q) (~a. ~b. a) (~a. ~b. a)") // true || true -> true
exec("(~p. ~q. p p q) (~a. ~b. a) (~a. ~b. b)") // true || false -> true
exec("(~p. ~q. p p q) (~a. ~b. b) (~a. ~b. a)") // false || true -> true
exec("(~p. ~q. p p q) (~a. ~b. b) (~a. ~b. b)") // false || false -> false
    
// `NOT`
exec("(~p. p (~a. ~b. b)(~a. ~b. a)) (~a. ~b. a)") // !true -> false
exec("(~p. p (~a. ~b. b)(~a. ~b. a)) (~a. ~b. b)") // !false -> true
println()

// `if ... else ...`
exec("(~p. ~a. ~b. p a b)(~a. ~b. a) (~x.one) (~y.two)") // (~x.one)
exec("(~p. ~a. ~b. p a b)(~a. ~b. b) (~x.one) (~y.two)") // (~y.two)
    
// Predicats
exec("(~n. n (~x. (~a. ~b. b))(~a. ~b. a)) (~f. ~x. x)") // 0 isZero? -> true
exec("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. f x)") // 1 isZero? -> false
exec("(~n. n (~x. (~a. ~b. b))(~a. ~b. a))(~f. ~x. f (f x))") // 2 isZero? -> false

// pair, first, second
exec("(~p. p (~x. ~y. x))((~x. ~y. ~z. z x y) (~x.first) (~y.second))") // (~x.first)
exec("(~p. p (~x. ~y. y))((~x. ~y. ~z. z x y) (~x.first) (~y.second))") // (~x.second)
```

### sbt project compiled with Dotty

For more information on the sbt-dotty plugin, see the
[dotty-example-project](https://github.com/lampepfl/dotty-example-project/blob/master/README.md).
