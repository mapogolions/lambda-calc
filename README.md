# Interpreter for lambda calculus


### For more information:

[What is lambda calculus](https://en.wikipedia.org/wiki/Lambda_calculus)
[Chuch encoding](https://en.wikipedia.org/wiki/Church_encoding)


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
scala> ...
```
Enter `:quit` for terminated session


## sbt project compiled with Dotty

### Usage

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL.

For more information on the sbt-dotty plugin, see the
[dotty-example-project](https://github.com/lampepfl/dotty-example-project/blob/master/README.md).
