/*
Homework 2
Domain Specific Language (DSL)
Set Theory
*/
import SetTheory.*
import SetTheory2.Expression
import scala.collection.mutable           //Importing scala.collection.mutable to be able to use val while dynamically updating the bindings
object SetTheory2:
  //the statement below creates a binding that maps 'String' datatype (object name) to 'Any' type (its value) using mutable.Map
  private val Binding: mutable.Map[String, Any] = mutable.Map("var"->10, "str"->"string", "aSetName"->mutable.Set(1,2,3,4,5), "setX"->mutable.Set(2,4,6),
    "set1"->mutable.Set(10,20,30), "set2"->mutable.Set(30,40,50))
  //The statements below define the usage of scopes
  //The Virtual Dispatch Table VDT contains class methods and their corresponding mappings
  private val VDT = mutable.Map[String, Expression]()
  //Stacks which store the results of executions of class instances
  private val ClassScope = mutable.Stack[Any]()
  private val Object1Scope = mutable.Stack[Any]()
  private val Object2Scope = mutable.Stack[Any]()
  private val ScopeStack = mutable.Stack[Any]("global")
  private val Flag = mutable.Map[String,Boolean]("flag"->false)
  private val params = Seq[Any]()

  //The following expressions are executed when a corresponding case match is found
  enum Expression:
    case Value(input: Any)
    case Variable(varName: String)
    case Assign(varName: String, value: Expression)
    case Insert(varName: String, element: Expression)
    case Delete(varName: String, element: Expression)
    case Union(set1: String, set2: String)
    case Intersection(set1: String, set2: String)
    case SetDifference(set1: String, set2: String)
    case SymmetricDifference(set1: String, set2: String)
    case CartesianProduct(set1: String, set2: String)

    case ClassDef(className: String, classContents: Expression*)
    case Field(fieldName: String)
    case Constructor(className: String, expressions: Expression*)
    case Method(methodName: String, methodsExp: Expression*)
    case NewObject(className: String, objName: String)
    case InvokeMethod(objName: String, methodName: String)
    case Extends(subClassName: String, superClassName: String)

    //This is basically the main function that contains the definitions of the set operations
    def eval: Any =
      this match {
        case Field(fieldName) =>
          ClassScope.push(fieldName->0)
          ClassScope.head

        //Constructor
        case Constructor(className, expressions*) =>
          for (exp <- expressions)
            if (ScopeStack.head=="obj1")
              Object1Scope.push(exp.eval)
              return Object1Scope.head
            else if (ScopeStack.head=="obj2")
              Object2Scope.push(exp.eval)
              return Object2Scope.head

        //Execution of the Method Construct
        case Method(methodName, methodsExp*) =>
          for (exp <- methodsExp)
            VDT += methodName->exp
            if (ScopeStack.head=="obj1")
              Object1Scope.push(exp.eval)
              return Object1Scope.head
            else if (ScopeStack.head=="obj2")
              Object2Scope.push(exp.eval)
              return Object2Scope.head

        //Defines the class definition and stores it in the blueprint in the mapping
        case ClassDef(className, classContents*) =>
          for (exp <- classContents)
            VDT += className->exp
            exp.eval
          ClassScope.head

        //Creates a new object and initializes the values defined in the class constructor
        case NewObject(className, objName) =>
          ScopeStack.push(objName)
          VDT += className->Value(objName)
          Constructor(className).eval
          VDT(className).eval

        //Invokes the method of the corresponding object
        case InvokeMethod(objName, methodName) =>
          if VDT.contains(methodName) then
            if (objName=="obj1")
              VDT(methodName).eval
              Object1Scope += VDT(methodName).eval
              return  Object1Scope.last
            else if (objName=="obj2")
              VDT(methodName).eval
              Object2Scope += VDT(methodName).eval
              return  Object2Scope.last

        //Implements the conventional 'extends' keyword
        //This expression gets in all the methods and variables of the superclass into the subclass
        case Extends(subClassName, superClassName) =>
          if (!Flag("flag"))
            Flag("flag")=true
            VDT += subClassName->VDT(superClassName)
            subClassName->VDT(superClassName)
          else
            "Multiple Inheritance is not allowed."


        //Returns the Value that is passed into it
        case Value(i) => i

        //Returns the corresponding value stored in the 'varName' if it exists or else assigns it a value of 0
        case Variable(varName) =>
          if (Binding.contains(varName))
            Binding(varName)
          else
            Binding += (varName->0)
            Binding(varName)

        //Assigns the 'value' to the variableName 'op1'
        case Assign(varName, value) =>
          Binding +=(varName->value.eval)
          Binding(varName)

        //Inserts an element into the set
        //Creates a new set if it doesn't exist
        case Insert(varName, element) =>
          if(Binding.contains(varName))
            val newAddition = Binding(varName).asInstanceOf[mutable.Set[Any]] + element.eval
            Binding += (varName -> newAddition)
            Binding(varName)
          else
            Binding += (varName -> mutable.Set(element.eval))
            mutable.Set(element.eval)

        //Deletes` an element from the set
        case Delete(varName, element) =>
          Binding(varName).asInstanceOf[mutable.Set[Any]] -= element.eval

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