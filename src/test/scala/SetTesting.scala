import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import SetTheory.SetExpression.*

import java.util.NoSuchElementException
import scala.collection.mutable

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
    Assign("setUnion", Union("set1","set2")).eval.asInstanceOf[Set[Any]] should contain allOf (10,20,30,40,50,60,70,80)
  }
  behavior of "Insert and Delete Operations"
  it should "add and remove objects from the set" in {
    Insert("aSetName", Value(6)).eval.asInstanceOf[Set[Any]] should contain allOf(1,2,3,4,5,6)
    Insert("s", Value(7)).eval.asInstanceOf[Set[Any]] should contain (7)
    Insert("s", Value(8)).eval.asInstanceOf[Set[Any]] should contain allOf(7,8)
    Insert("s", Value(9)).eval.asInstanceOf[Set[Any]] should contain allOf(7,8,9)
//    Delete("s", Value(9)).eval.asInstanceOf[mutable.Set[Any]] shouldBe mutable.Set(7,8)
  }
  behavior of "Set Operations"
  it should "find the union, intersection, difference, symmetric difference and cartesian product of the given sets" in {
    Union("set1","set2").eval.asInstanceOf[Set[Any]] should contain allOf (10,20,30,40,50,60,70,80)
    Intersection("set1","set2").eval.asInstanceOf[Set[Any]] should contain allOf (40,50)
    SetDifference("set1","set2").eval.asInstanceOf[Set[Any]] should contain allOf (10,20,30)
    SymmetricDifference("set1","set2").eval.asInstanceOf[Set[Any]] should contain allOf (10, 20, 30, 60, 70, 80)
//    CartesianProduct("set1","set2").eval.asInstanceOf[Set[Any]] should contain allOf (10,20,30,40,50,60,70,80)
  }
}
