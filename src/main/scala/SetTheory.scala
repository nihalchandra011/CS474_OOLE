import scala.collection.mutable
object SetTheory:
  val Binding: mutable.Map[String, Any] = mutable.Map("var"->10, "str"->"string", "aSetName"->Set(1,2,3,4,5),
    "set1"->Set(10,20,30,40,50), "set2"->Set(40,50,60,70,80))

  enum SetExpression:
    case Value(input: Any)
    case Variable(varName: String)
    case Check(setName: String, element:SetExpression)
    case Assign(op1: String, value: SetExpression)
    case Insert(op1: String, element: SetExpression)
    case Delete(varName: String, element: SetExpression)
    case Union(set1: String, set2: String)
    case Intersection(set1: String, set2: String)
    case SetDifference(set1: String, set2: String)
    case SymmetricDifference(set1: String, set2: String)
    case CartesianProduct(set1: String, set2: String)

    def eval: Any =
      this match {
        case Value(i) => i

        case Variable(varName) =>
          if Binding.contains(varName) then
            Binding(varName)
          else
            Binding += (varName->0)
            Binding(varName)

        case Check(setName, element) =>
          Binding(setName).asInstanceOf[Set[Any]].contains(element.eval)

        case Assign(op1, value) =>
          Binding +=(op1->value.eval)
          Binding(op1)

        case Insert(op1, element) =>
          if(Binding.contains(op1))
            val newAddition = Binding(op1).asInstanceOf[Set[Any]] + element.eval
              Binding += (op1 -> newAddition)
            newAddition
          else
            Binding += (op1 -> Set(element.eval))
            Set(element.eval)

        case Delete(varName, element) =>
          Binding(varName).asInstanceOf[mutable.Set[Any]] -= element.eval

        case Union(set1, set2) =>
          Binding(set1).asInstanceOf[Set[Any]].union(Binding(set2).asInstanceOf[Set[Any]])

        case Intersection(set1, set2) =>
          Binding(set1).asInstanceOf[Set[Any]].intersect(Binding(set2).asInstanceOf[Set[Any]])

        case SetDifference(set1, set2) =>
          Binding(set1).asInstanceOf[Set[Any]].diff(Binding(set2).asInstanceOf[Set[Any]])

        case SymmetricDifference(set1, set2) =>
          Binding(set1).asInstanceOf[Set[Any]].diff(Binding(set2).asInstanceOf[Set[Any]]).
            union(Binding(set2).asInstanceOf[Set[Any]].diff(Binding(set1).asInstanceOf[Set[Any]]))

        case CartesianProduct(set1, set2) =>
          val new_binding = mutable.Set[Any]()
          for (a<-Binding(set1).asInstanceOf[Set[Any]])
            for (b<-Binding(set2).asInstanceOf[Set[Any]])
              new_binding += ((a, b))
          new_binding
      }

  @main def runExpression(): Unit =
    import SetExpression.*
      println(CartesianProduct("set1","set2").eval.asInstanceOf[Set[Any]])
