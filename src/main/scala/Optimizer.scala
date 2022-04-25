import SetTheory5.Operations.*

object Optimizer:
  def function(x:SetTheory5.Operations): Any = x match {
    //Union of two sets
    case Union(x,y) =>
      if (x.asInstanceOf[SetTheory5.Operations].eval == y.asInstanceOf[SetTheory5.Operations].eval) x
      else if (x.asInstanceOf[SetTheory5.Operations].eval ==  Set() && y.asInstanceOf[SetTheory5.Operations].eval == Set()) Set()
      else if(y.asInstanceOf[SetTheory5.Operations].eval == Set()) x
      else if(x.asInstanceOf[SetTheory5.Operations].eval ==  Set()) y
      else Union(x,y).eval

    //Intersection of two sets
    case Intersection(x,y) =>
      if (x.asInstanceOf[SetTheory5.Operations].eval == y.asInstanceOf[SetTheory5.Operations].eval) x
      else if (x.asInstanceOf[SetTheory5.Operations].eval ==  Set() || y.asInstanceOf[SetTheory5.Operations].eval == Set()) Set()
      else Intersection(x,y)

    //SetDifference of two sets
    case SetDifference(x,y) =>
      if (x.asInstanceOf[SetTheory5.Operations].eval == y.asInstanceOf[SetTheory5.Operations].eval) Set()
      else if (x.asInstanceOf[SetTheory5.Operations].eval ==  Set() && y.asInstanceOf[SetTheory5.Operations].eval == Set()) Set()
      else if(y.asInstanceOf[SetTheory5.Operations].eval == Set()) x
      else if(x.asInstanceOf[SetTheory5.Operations].eval ==  Set()) Set()
      else SetDifference(x,y).eval

    //SymmetricDifference of two sets
    case SymmetricDifference(x,y) =>
      if (x.asInstanceOf[SetTheory5.Operations].eval == y.asInstanceOf[SetTheory5.Operations].eval) Set()
      else if (x.asInstanceOf[SetTheory5.Operations].eval ==  Set() && y.asInstanceOf[SetTheory5.Operations].eval == Set()) Set()
      else if(y.asInstanceOf[SetTheory5.Operations].eval == Set()) x
      else if(x.asInstanceOf[SetTheory5.Operations].eval ==  Set()) y
      else SymmetricDifference(x,y).eval
  }
  def function1(y:SetTheory5.Operations): Any = y match {
    case IF(condition, thenClause, elseClause) =>
      val condition1 = condition.eval
      if (condition1 == true) thenClause
      else elseClause
  }

  trait SetExpression:
    def map(f: SetExpression => SetExpression): SetExpression

  @main def runOpt(): Unit =
    import Optimizer.*
    val exp = Set(Union(Variable("set1"),Variable("set1")))
    println(exp.map(function))

