/*
Homework 1
Domain Specific Language (DSL)
Set Theory
*/
import scala.collection.mutable           //Importing scala.collection.mutable to be able to use val while dynamically updating the bindings
object SetTheory:
  //the statement below creates a binding that maps 'String' datatype (object name) to 'Any' type (its value) using mutable.Map
  private val Binding: mutable.Map[String, Any] = mutable.Map("var"->10, "str"->"string", "aSetName"->mutable.Set(1,2,3,4,5),
    "set1"->mutable.Set(10,20,30), "set2"->mutable.Set(30,40,50))
  //the statement below creates a binding that maps macro name to its corresponding function
  private val MacroBinding: mutable.Map[String, SetExpression] = mutable.Map[String,SetExpression]()
  
  private val SetScopeBinding : mutable.Map[String,Set[Any]]= mutable.Map()
  private val ScopeBinding : mutable.Map[String, mutable.Map[String,Set[Any]]] = mutable.Map()

  //The following expressions are executed when a corresponding case match is found
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
    case assignMacro(macroName: String, macroOp: SetExpression)
    case resolveMacro(macroName: String)
    case Scope(scopeName:String,setName:SetExpression,one:SetExpression,two:SetExpression)
    case NestedScope(outerScopeName:String,innerScopeExp:SetExpression,outerOne:SetExpression,outerTwo:SetExpression, outerThree:SetExpression)

    //The main function that contains the definitions of the set operations
    def eval: Any =
      this match {
        //Returns the Value that is passed into it
        case Value(i) => i

        //Returns the corresponding value stored in the 'varName' if it exists or else assigns it a value of 0
        case Variable(varName) =>
          if Binding.contains(varName) then
            Binding(varName)
          else
            Binding += (varName->0)
            Binding(varName)

        //Checks if an element is contained in a set or not
        case Check(setName, element) =>
          Binding(setName).asInstanceOf[mutable.Set[Any]].contains(element.eval)

        //Assigns the 'value' to the variableName 'op1'
        case Assign(op1, value) =>
          Binding +=(op1->value.eval)
          Binding(op1)

        //Inserts an element into the set
        //Creates a new set if it doesn't exist
        case Insert(op1, element) =>
          if(Binding.contains(op1))
            val newAddition = Binding(op1).asInstanceOf[mutable.Set[Any]] + element.eval
              Binding += (op1 -> newAddition)
            Binding(op1)
          else
            Binding += (op1 -> mutable.Set(element.eval))
            mutable.Set(element.eval)

        //Deletes an element from the set
        case Delete(varName, element) =>
          Binding(varName).asInstanceOf[mutable.Set[Any]] -= element.eval

        //Assigns the SetExpression contained in 'marcoOp' to the 'macroName'
        case assignMacro(macroName, macroOp) =>
          MacroBinding += (macroName -> macroOp)

        //Resolves the 'macroName' and implements its corresponding SetExpression
        case resolveMacro(macroName) =>
          MacroBinding(macroName).eval

        //Creates a scope with the 'setName' set and the values 'one' and 'two'
        case Scope(scopeName,setName,one,two) =>
          SetScopeBinding += (setName.eval.asInstanceOf[String]-> Set(one.eval,two.eval))
          if (ScopeBinding.contains(scopeName)){
            if (ScopeBinding(scopeName).contains(setName.eval.asInstanceOf[String])) {
              ScopeBinding(scopeName)(setName.eval.asInstanceOf[String]) =
                ScopeBinding(scopeName)(setName.eval.asInstanceOf[String]) ++ SetScopeBinding(setName.eval.asInstanceOf[String])
              ScopeBinding(scopeName)
            }
            else {
              ScopeBinding(scopeName) = ScopeBinding(scopeName) + (setName.eval.asInstanceOf[String]->SetScopeBinding(setName.eval.asInstanceOf[String]))
              ScopeBinding(scopeName)
            }
          }
          else {
            ScopeBinding += (scopeName -> collection.mutable.Map(setName.eval.asInstanceOf[String] ->
              SetScopeBinding(setName.eval.asInstanceOf[String])))
            ScopeBinding(scopeName)
          }

        //Evaluates the innerScopeExp first and the inner variables are inaccessible in the outer scope
        case NestedScope(outerScopeName,innerScopeExp,outerOne,outerTwo, outerThree) =>
          innerScopeExp.eval
          Scope(outerScopeName,outerOne,outerTwo, outerThree).eval
          ScopeBinding

        //Returns the Union of 'set1' and 'set2'
        case Union(set1, set2) =>
          Binding(set1).asInstanceOf[mutable.Set[Any]].union(Binding(set2).asInstanceOf[mutable.Set[Any]])

        //Returns the Intersection of 'set1' and 'set2'
        case Intersection(set1, set2) =>
          Binding(set1).asInstanceOf[mutable.Set[Any]].intersect(Binding(set2).asInstanceOf[mutable.Set[Any]])

        //Returns the Set Difference of 'set1' and 'set2'
        case SetDifference(set1, set2) =>
          Binding(set1).asInstanceOf[mutable.Set[Any]].diff(Binding(set2).asInstanceOf[mutable.Set[Any]])

        //Returns the Symmetric Difference of 'set1' and 'set2'
        case SymmetricDifference(set1, set2) =>
          Binding(set1).asInstanceOf[mutable.Set[Any]].diff(Binding(set2).asInstanceOf[mutable.Set[Any]]).
            union(Binding(set2).asInstanceOf[mutable.Set[Any]].diff(Binding(set1).asInstanceOf[mutable.Set[Any]]))

        //Returns the Cartesian Product of 'set1' and 'set2'
        case CartesianProduct(set1, set2) =>
          val new_binding = mutable.Set[Any]()
          for (a<-Binding(set1).asInstanceOf[mutable.Set[Any]])
            for (b<-Binding(set2).asInstanceOf[mutable.Set[Any]])
              new_binding += ((a, b))
          new_binding
      }
