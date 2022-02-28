import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import SetTheory2.Expression.*
//There are 8 test cases each of which test different functionalities of the language
class SetTesting2 extends AnyFlatSpec with Matchers {

  behavior of "Class Definition and Object Creation"
  it should "define a class and instantiate it" in {
    //A class 'someClassName' is defined that contains a Field 'f', Constructor that assigns value of f=1, method m1 that returns 1 and method m2 that returns the union of sets set1 and set2
    ClassDef("someClassName", Field("f"), Constructor("someClassName", Assign("f", Value(1))), Method("m1", Value(1)), Method("m2", Union("set1", "set2"))).eval //shouldEqual (f,0)
    //Instantiating the class someClassName using the NewObject datatype
    NewObject("someClassName", "obj1").eval shouldEqual "obj1"
  }
  behavior of "Nested Classes"
  it should "define an innerClass in an outerClass and be able to access using corresponding objects" in {
    ClassDef("outerClassName", Field("fo"), Constructor("outerClassName", Assign("fo", Value(3))), Method("m5", Value(5), Union("set1","set2")), ClassDef("nestedClassName", Field("fi"), Constructor("nestedClassName", Assign("fi", Value(4))), Method("m6", Value(6)))).eval
    //Creating a new object of the outer class
    NewObject("outerClassName", "obj1").eval shouldEqual "obj1"
    //Creating a new object of the inner class
    NewObject("nestedClassName", "obj2").eval shouldEqual "obj2"
    //In the statement below, we are accessing the method 'm5' from outer class object ojb1
    InvokeMethod("obj1", "m5").eval shouldEqual 5
    //In the statement below, we are accessing the method 'm6' from inner class object ojb2
    InvokeMethod("obj2", "m6").eval shouldEqual 6
  }
  behavior of "Simple Inheritance using Extends and InvokeMethod"
  it should "extend the super class and be able to access it using the instance of sub class" in {
    ClassDef("superClassName", Field("f1"), Constructor("superClassName", Assign("f1", Value(1))), Method("m1", Value(10)), Method("m2", Union("set1", "set2"))).eval
    ClassDef("subClass", Field("f2"), Constructor("subClass", Assign("f2", Value(2))), Method("m3", Value(30)), Method("m4", Intersection("set1", "set2"))).eval
    Extends("subClass","superClassName").eval
    NewObject("subClassName", "obj1").eval  shouldEqual "obj1"
    NewObject("subClassName", "obj2").eval  shouldEqual "obj2"
    InvokeMethod("obj2", "m3").eval shouldEqual 30
    InvokeMethod("obj2", "m1").eval  shouldEqual 10
  }
  behavior of "Inheritance using Nested Classes"
  it should "allow the inherited class object to access the methods (containing multiple operations) of nestedClass of superClass" in {
    ClassDef("outerClassName", Field("fo"), Constructor("outerClassName", Assign("fo", Value(3))), Method("m1", Value(5)),
      ClassDef("nestedClassName", Field("fi"), Constructor("nestedClassName", Assign("fi", Value(4))), Method("m2", Value(6), Union("set1","set2")))).eval
    ClassDef("subClass", Field("f2"), Constructor("subClass", Assign("f2", Value(2))), Method("mSub2", Value(10)), Method("mSub2", Intersection("set1", "set2"))).eval
    Extends("subClass","outerClassName").eval
    NewObject("subClass", "obj1").eval shouldEqual "obj1"
    NewObject("subClass", "obj2").eval shouldEqual "obj2"
    InvokeMethod("obj1", "m1").eval shouldEqual 5
    InvokeMethod("obj2", "m2").eval shouldEqual  6
  }
  behavior of "Multiple Inheritance"
  it should "not allow multiple inheritance" in {
    ClassDef("Class1", Field("f1"), Constructor("Class1", Assign("f1", Value(1))), Method("m1", Value(10)), Method("m11", Union("set1", "set2"))).eval
    ClassDef("Class2", Field("f2"), Constructor("Class2", Assign("f2", Value(2))), Method("m2", Value(20)), Method("m22", Intersection("set1", "set2"))).eval
    ClassDef("Class3", Field("f3"), Constructor("Class3", Assign("f3", Value(3))), Method("m3", Value(30)), Method("m33", SetDifference("set1", "set2"))).eval
    Extends("Class3","Class1").eval
    //If the subclass (Class 3) tries to inherit from multiple classes (Class1 and Class2) it gives an error message
    Extends("Class3","Class2").eval shouldEqual "Multiple Inheritance is not allowed."
  }
  behavior of "Method Overriding during Inheritance"
  it should "be able to execute the appropriate method when two methods have the same name" in {
    ClassDef("Class1", Field("f1"), Constructor("Class1", Assign("f1", Value(1))), Method("sameMethodName", Value(10)), Method("m11", Union("set1", "set2"))).eval
    ClassDef("Class2", Field("f2"), Constructor("Class2", Assign("f2", Value(2))), Method("sameMethodName", Value(20)), Method("m22", Intersection("set1", "set2"))).eval
    Extends("Class2","Class1").eval
    //When we try to access the sameMethodName of class2, the method corresponding to that class is executed and hence it returns 20
    NewObject("Class2", "obj2").eval shouldEqual "obj2"
    InvokeMethod("obj1", "sameMethodName").eval shouldEqual 20
    //It doesn't return anything when we try to access a method that does not exist
    NewObject("Class2", "obj3").eval shouldEqual "obj3"
    InvokeMethod("obj3", "nonExistentMethod").eval shouldEqual()
  }
}