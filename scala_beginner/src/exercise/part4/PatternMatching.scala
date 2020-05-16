package exercise.part4

object PatternMatching extends App {

  /*
    take an Express => human readable format
    Sum(Number(2), Number(3)) => 2 + 3
    Prod(Sum(Number(2), Number(1), Number(3)) => (2+1) * 3
   */

  trait Expr
  case class Number(n : Int) extends  Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + "+" + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }

      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }

  println(show(Sum(Number(1), Number(2))))
  println(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4))))

}
