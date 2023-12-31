package edu.neu.coe.csye7200.numerics

/**
  * @author scalaprof
  *
  *         This trait defines a differentiable function and is used by Fuzzy
  */
trait DiFunc[X] {
  /**
    * @return the number of independent variables in the function
    */
  def arity: Int

  /**
    * @return the function which transforms a value into a different value (THE function)
    */
  def f: X => X

  /**
    * @param i which variable we want the partial derivative for (0<=i<arity)
    * @return the partial derivative of f with respect to the ith variable
    */
  def df_dx(i: Int): X => Double
}

/**
  * Base class for trait DiFunc
  *
  * @param g  function which transforms value (THE function)
  * @param ds a variable number of one-functions
  */
abstract class DiFuncBase[X](g: X => X, ds: (X => Double)*) extends DiFunc[X] {
  def arity: Int = ds.size

  def f: X => X = g

  def df_dx(i: Int): X => Double =
    if (i < arity) ds(i)
    else throw new UnsupportedOperationException(s"no partial derivative defined for $i")
}

case class Power(y: Double) extends DiFuncBase[Double](x => math.pow(x, y), x => y * math.pow(x, y - 1), x => math.log(x) * math.pow(x, y))

case class Times(y: Double) extends DiFuncBase[Double](x => x * y, _ => y, x => x)

case object Negative extends DiFuncBase[Double](x => -x, _ => -1.0)

case object Inverse extends DiFuncBase[Double](x => 1.0 / x, x => -1.0 / x / x)