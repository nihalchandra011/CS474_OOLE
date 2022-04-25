import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import SetTheory5.Operations.*
import Optimizer.*

//There are 7 test cases each of which test different functionalities of the language
class SetTesting5 extends AnyFlatSpec with Matchers {

  behavior.of("Variable's Partial Evaluation")
  it.should("return the Variable construct without evaluating if the Binding of the variable is missing") in {
    Assign("x", 10).eval
    Variable("x").eval shouldBe 10 //returns 10 as x is present in Binding
    Variable("y").eval shouldBe Variable("y") //returns Variable("y") as y is not present in Binding
  }

  behavior.of("Check Construct - Partial Evaluation")
  it.should("return the partially evaluated Check construct if the Binding of any variable is missing") in {
    Assign("a", 10).eval
    Assign("b", 10).eval
    Assign("c", 20).eval
    Check("a", "b").eval shouldBe true //Evaluates the expression as usual and returns true since both a and b are present and equal
    Check("a", "c").eval shouldBe false //Evaluates the expression as usual and returns false since both a and b are present and unequal
    Check("a", "d").eval shouldBe Check(10, "d") //Partially evaluates a as 10 but returns d as it is, since d is not present in binding
  }

  behavior.of("CheckSet construct - Partial Evaluation")
  it.should("return the partially evaluated CheckSet construct if the Binding of any variable is missing") in {
    Assign("v", 3).eval
    Assign("setX", mutable.Set(1, 2, 3, 4)).eval
    Insert("setX", Value(5)).eval
    CheckSet("setX", "v").eval shouldBe true //Evaluates the expression as usual and returns true since both a and b are present and equal
    CheckSet("newSet", "v").eval shouldBe CheckSet("newSet", 3) //Evaluates the expression as usual and returns false since both a and b are present and unequal
  }

  behavior.of("Insert and Delete constructs - Partial Evaluation")
  it.should("only evaluate the insert and delete statements partially if the binding does not exist") in {
    Assign("setA", mutable.Set(1, 2, 3, 4)).eval
    Insert("setA", Value(5)).eval shouldBe mutable.HashSet(1, 2, 3, 4, 5) //Normal Execution
    Insert("anotherNewSet", Value(10)).eval shouldBe Insert("anotherNewSet", 10) //Partial Evaluation
    Delete("setA", Value(5)).eval shouldBe mutable.HashSet(1, 2, 3, 4) //Normal Execution
    Delete("randomSet", Value(4)).eval shouldBe Delete("randomSet", 4) //Partial Evaluation
  }

  behavior.of("All SetOperation constructs - Partial Evaluation")
  it.should("only evaluate the set operation statements partially if the binding does not exist") in {
    Union(Variable("set1"), Variable("set2")).eval shouldBe mutable.HashSet(50, 20, 40, 10, 30) //Case 1 - Both sets exist in Binding - Normal Execution
    Union(Variable("randomSet1"), Variable("set2")).eval shouldBe Union(Variable("randomSet1"), mutable.HashSet(40, 50, 30)) //Case 2 - First set does not exist in Binding - Partial Evaluation
    Union(Variable("set1"), Variable("randomSet2")).eval shouldBe Union(mutable.HashSet(10, 20, 30),Variable("randomSet2"))  //Case 3 - Second set does not exist in Binding - Partial Evaluation
    Union(Variable("randomSet3"), Variable("randomSet4")).eval shouldBe Union(Variable("randomSet3"),Variable("randomSet4"))  //Case 4 - Both sets do not exist in Binding - No Evaluation

    Intersection(Variable("set1"), Variable("set2")).eval shouldBe mutable.HashSet(30) //Case 1 - Both sets exist in Binding - Normal Execution
    Intersection(Variable("someSet"), Variable("set2")).eval shouldBe Intersection(Variable("someSet"), mutable.HashSet(40, 50, 30)) //Case 2 - First set does not exist in Binding - Partial Evaluation
    Intersection(Variable("set1"), Variable("someSet1")).eval shouldBe Intersection(mutable.HashSet(10, 20, 30),Variable("someSet1")) //Case 3 - Second set does not exist in Binding - Partial Evaluation
    Intersection(Variable("someSet2"), Variable("someSet3")).eval shouldBe Intersection(Variable("someSet2"),Variable("someSet3"))  //Case 4 - Both sets do not exist in Binding - No Evaluation

    //The code works similarly for other set operations as well
    SetDifference(Variable("set1"), Variable("set2")).eval shouldBe mutable.HashSet(20, 10) //Normal Execution
    SetDifference(Variable("set1"), Variable("aSet")).eval shouldBe SetDifference(mutable.HashSet(10, 20, 30), Variable("aSet")) //Partial Evaluation
  }

  behavior.of("New Object construct - Partial Evaluation")
  it.should("return the same expression if the class for which object is created does not exist") in {
    NewObject("nonExistentClass", "newObj").eval shouldBe NewObject("nonExistentClass", "newObj")
  }

  behavior.of("InvokeMethod construct - Partial Evaluation")
  it.should("return the same expression if the object through which the method is invoked does not exist") in {
    ClassDef("ClassA", Field("fa"), Constructor(Assign("fa", Value(10))), Method("m5", Value(5))).eval
    NewObject("ClassA", "obj1").eval
    InvokeMethod("obj1","m5").eval shouldBe 5                            //Normal Execution since object exists
    InvokeMethod("obj2","m5").eval shouldBe InvokeMethod("obj2","m5")    //Returns the same expression since the object does not exist
  }

  behavior.of("Optimization Functions")
  it.should("return only one set when the union of same set is done") in {
    val exp1 = Set(Union(Variable("set1"),Variable("set1")))
    exp1.map(function) shouldBe Set(Variable("set1"))
  }
  
}
