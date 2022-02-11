import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import SetTheory.SetExpression.*

class SetTesting extends AnyFlatSpec with Matchers {
  behavior of "Check Operation"
  it should "check if an element is present in a set or not" in {
    Check("aSetName", Value(1)).eval shouldBe true
    Check("aSetName", Value(100)).eval shouldBe false
  }
  behavior of "Value Operation"
  it should "return the same value (Int/Double/String) that is passed into it" in {
    Value(10).eval shouldBe 10
    Value(10.5).eval shouldBe 10.5
    Value("ten").eval shouldBe "ten"
    Value(Set(1,2,3)).eval.asInstanceOf[Set[Any]] should contain allOf(1,2,3)
  }

  behavior of "Variable Operation"
  it should "return the value stored in that variable if the variable exists or else default it to zero" in {
    Variable("var").eval should equal(10)
    Variable("str").eval should equal("string")
    Variable("aSetName").eval should equal(Set(1, 2, 3, 4, 5))
    Variable("newVar").eval should equal(0)
  }
  behavior of "Assign Operation"
  it should "assign the value to the Set/Variable or create a new one if it does not exist" in {
    Assign("asset1", Value(Set(6,7,8))).eval should equal(Set(6,7,8))
    Assign("asset2", Value(Set("x", 1,"y",2,"z",3))).eval should equal(Set("x", 1,"y",2,"z",3))
    Assign("var1",Value(15)).eval should equal(15)
    Assign("var2",Variable("var")).eval should equal(10)
    Assign("setUnion", Union("set1","set2")).eval.asInstanceOf[mutable.Set[Any]] should contain allOf (10,20,30,40,50)
  }
  behavior of "Insert and Delete Operations"
  it should "add and remove objects from the set" in {
    Insert("aSetName", Value(6)).eval.asInstanceOf[mutable.Set[Any]] should contain allOf(1,2,3,4,5,6)
    Insert("s", Value(7)).eval.asInstanceOf[mutable.Set[Any]] should contain (7)
    Insert("s", Value(8)).eval.asInstanceOf[mutable.Set[Any]] should contain allOf(7,8)
    Insert("s", Value(9)).eval.asInstanceOf[mutable.Set[Any]] should contain allOf(7,8,9)
    Delete("s", Value(9)).eval.asInstanceOf[mutable.Set[Any]] should contain allOf(7,8)
  }
  behavior of "Macro Operation"
  it should "implement the operation assigned to the Macro name" in {
    Assign("x", Value(mutable.Set(10,15,20))).eval
    assignMacro("DeleteFromSet", Delete("x", Value(15))).eval
    Assign("r", resolveMacro("DeleteFromSet")).eval
    Variable("r").eval.asInstanceOf[mutable.Set[Any]] should contain allOf(10,20)
  }
  behavior of "Scopes"
  it should "prevent the elements present inside the inner scope to be accessed in the outer scope" in {
    NestedScope("outerScope",Scope("innerScope",Value("set1"),Variable("var1"),Value(5)),Value("set2"),Value(10),Value("set3")).eval shouldEqual mutable.HashMap("innerScope" -> mutable.HashMap("set1" -> Set(0, 5)), "outerScope" -> mutable.HashMap("set2" -> Set(10, "set3")))
    Scope("aName",Value("set1"),Value(100),Value("aString")).eval shouldEqual Map("set1" -> Set(100, "aString"))
    Scope("anotherName",Value("set1"),Value(13),Value("anotherString")).eval shouldEqual mutable.HashMap("set1" -> Set(13, "anotherString"))
  }
  behavior of "Set Operations"
  it should "find the union, intersection, difference, symmetric difference and cartesian product of the given sets" in {
    Union("set1","set2").eval.asInstanceOf[mutable.Set[Any]] should contain allOf (10,20,30,40,50)
    Intersection("set1","set2").eval.asInstanceOf[mutable.Set[Any]] should contain (30)
    SetDifference("set1","set2").eval.asInstanceOf[mutable.Set[Any]] should contain allOf (10,20)
    SymmetricDifference("set1","set2").eval.asInstanceOf[mutable.Set[Any]] should contain allOf (10, 20, 40, 50)
    CartesianProduct("set1","set2").eval.asInstanceOf[mutable.Set[Any]] should contain allOf((10,30), (10,50), (10,40), (30,50), (20,40), (30,30), (30,40), (20,30), (20,50))
  }
}