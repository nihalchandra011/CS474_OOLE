import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import SetTheory4.Operations.*
//There are 7 test cases each of which test different functionalities of the language
class SetTesting4 extends AnyFlatSpec with Matchers {

  behavior of "IF construct's parameters' execution"
  it should "ensure proper flow of execution for check, thenClause and elseClause" in {
    Check("var", Value(10)).eval shouldEqual true
    Check("str", Value("string")).eval shouldEqual true
    IF(Check("var",Value(10)), Set(Insert("someSet", Value(100))), Set(Insert("anotherSet", Value(200)))).eval shouldBe ("someSet",mutable.HashSet(100))
    IF(Check("var",Value(100)), Set(Insert("someSet", Value(100))), Set(Insert("anotherSet", Value(200)))).eval shouldBe ("anotherSet",mutable.HashSet(200))
  }

  behavior of "IF construct"
  it should "ensure proper flow of execution based on the evaluated condition" in {
    IF(Check("var",Value(10)), Set(Insert("newSet", Value(1))), Set(Insert("anotherNewSet", Value(2)), Insert("anotherNewSet", Value(3)))).eval shouldBe ("newSet",mutable.HashSet(1))
    IF(Check("var",Value(100)), Set(Insert("newSet", Value(1))), Set(Insert("anotherNewSet", Value(2)), Insert("anotherNewSet", Value(3)))).eval shouldBe ("anotherNewSet",mutable.HashSet(2))
    IF(Check("str",Value("string")), Set(Insert("newSet", Value(1))), Set()).eval shouldBe ("newSet",mutable.HashSet(1))
    IF(Check("str",Value("absent string")), Set(Insert("newSet", Value(1))), Set()).eval shouldBe "IF"
  }

  behavior of "Nested IF construct"
  it should "ensure proper flow of execution based on the evaluated condition of both outer and inner nested if-else" in {
    IF(Check("var",Value(10)), Set(Insert("newSet", Value(1)), IF(Check("var",Value(45)), Set(Insert("nestedSet1", Value(15))), Set(Insert("nestedSet2", Value(16))))), Set(Insert("outerSet", Value(21)))).eval shouldBe ("newSet",mutable.HashSet(1))
    IF(Check("var",Value(100)), Set(Insert("newSet", Value(1)), IF(Check("var",Value(45)), Set(Insert("nestedSet1", Value(15))), Set(Insert("nestedSet2", Value(16))))), Set(Insert("outerSet", Value(25)))).eval shouldBe ("outerSet",mutable.HashSet(25))
    IF(Check("var",Value(50)), Set(Insert("newSet", Value(1))), Set(Insert("outerSet", Value(25)), IF(Check("var",Value(45)), Set(Insert("nestedSet1", Value(15))), Set(Insert("nestedSet2", Value(16)))))).eval shouldBe ("outerSet",mutable.HashSet(25))
  }
  behavior of "Exception Handing - ExceptionClassDef Construct"
  it should "create a mapping of the reason with the Exception Class Name" in {
    ExceptionClassDef("someExceptionClassName", Field("Reason")).eval shouldBe (mutable.HashMap("someExceptionClassName" -> Map("Reason" -> "None")))
  }
  behavior of "Basic Try - Catch Block Execution"
  it should "Execute the contents of the catch block when try raises an error" in {
    Insert("setOne", Value(35)).eval
    ExceptionClassDef("someExceptionClassName", Field("Reason")).eval
    CatchException("someExceptionClassName",
      Set(ThrowException(ClassDef("someExceptionClassName"), Assign("Reason", "Check failed")), Insert("bSet", Value(40))),
      Set(Catch(Variable("storageOfException"), Insert("reasonSet", Field("Reason")), Insert("setOne", Value(36))))).eval
    Check("setOne",Value(Set(35,36))).eval shouldBe true
  }

  behavior of "Try-Catch: Execute entire try block without any exception"
  it should "execute all expressions in try block when there is no exception raised and not go into catch block" in {
    Insert("setTwo", Value(9)).eval
    ExceptionClassDef("someExceptionClassName", Field("Reason")).eval
    CatchException("someExceptionClassName",
      Set(Insert("setTwo", Value(10)), Insert("anotherTestSet", Value(20))),
      Set(Catch(Variable("storageOfException"), Insert("reasonSet", Field("Reason")), Insert("dummySet", Value(11))))).eval
    Check("setTwo",Value(Set(9,10))).eval shouldBe true
  }
  behavior of "Try-Catch: Checking if the code after throwing exception in try doesn't execute"
  it should "not execute the code part present after throwing an exception" in {
    Insert("setThree", Value(5)).eval
    ExceptionClassDef("ExceptionClass", Field("Reason")).eval
    CatchException("ExceptionClass",
      Set(Insert("setThree", Value(15)), ThrowException(ClassDef("ExceptionClass"), Assign("Reason", "Check failed")), Insert("setThree", Value(40))),
     Set(Catch(Variable("storageOfException"), Insert("reasonSet", Field("Reason")), Insert("catchSet", Value("catchValue"))))).eval
    Check("setThree",Value(Set(5,15))).eval shouldBe true
  }
}