import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import SetTheory3.Operations.*
class SetTesting3 extends AnyFlatSpec with Matchers {

  behavior of "Abstract Class and Interface cannot be initiated"
  it should "give an error message when an Abstract Class or Interface is initiated" in {
    AbstractClassDef("abstractClass1", Field("f"), Constructor(Assign("f", Value(2))), Method("m1", Value(1)), AbstractMethod("m2")).eval
    InterfaceDecl("I1",AbstractMethod("abm1"),AbstractMethod("abm2")).eval
    NewObject("abstractClass1", "obj1").eval shouldEqual "abstractClass1 cannot be instantiated."
    NewObject("I1", "obj2").eval shouldEqual "I1 cannot be instantiated."
  }

  behavior of "Child class should implement all abstract methods or else error (extends, implements)"
  it should "give an error message when all the abstract methods are not implemented" in {
    AbstractClassDef("abstractClass2", Field("f"), Constructor(Assign("f", Value(2))), Method("m1", Value(1)), AbstractMethod("m2")).eval
    InterfaceDecl("I2",AbstractMethod("abm1"),AbstractMethod("abm2")).eval
    ClassDef("classA", Field("f1"), Constructor(Assign("f1", Value(1))), Method("m3", Value(3))).eval
    Extends("classA","abstractClass2").eval shouldEqual "Please implement all abstract methods."
    Implements("classA","I2").eval shouldEqual "Please implement all abstract methods."
  }

  behavior of "An Interface cannot implement another Interface"
  it should "give an error message when an interface tries to implement another interface" in {
    InterfaceDecl("I3",AbstractMethod("abm1"),AbstractMethod("abm2")).eval
    InterfaceDecl("I4",AbstractMethod("abm1"),AbstractMethod("abm2")).eval
    Implements("I4","I3").eval shouldEqual "An interface cannot implement another interface."
  }

  behavior of "Interface cannot inherit from a pure abstract class"
  it should "give an error message when an interface inherits from a pure abstract class" in {
    AbstractClassDef("abstractClass3", Field("f"), Constructor(Assign("f", Value(2))),AbstractMethod("m2")).eval
    InterfaceDecl("I5",AbstractMethod("abm1"),AbstractMethod("abm2")).eval
    Extends("I5","abstractClass3").eval shouldEqual "Interface cannot inherit from a pure abstract class."
  }

  behavior of "Abstract Class inherits from another abstract class and a sub class will implement the unimplemented abstract methods"
  it should "ensure that the concrete sub class implements all the abstract methods" in {
    AbstractClassDef("abstractClass4", Field("f1"), Constructor(Assign("f1", Value(1))), Method("m1", Value(1)), AbstractMethod("m2")).eval
    AbstractClassDef("abstractClass5", Field("f2"), Constructor(Assign("f2", Value(2))), Method("m3", Value(3)), Method("m2", Union("set1","set2")), AbstractMethod("m4")).eval
    Extends("abstractClass5","abstractClass4").eval
    ClassDef("classB", Field("fa"), Constructor(Assign("fa", Value(10))), Method("m5", Value(5))).eval
    Extends("classB","abstractClass5").eval shouldEqual "Please implement all abstract methods."
  }

  behavior of "Abstract Class implements interface partially and sub class implements the remaining abstract methods fully."
  it should "define a class and instantiate it" in {
    InterfaceDecl("I6",AbstractMethod("abm1"),AbstractMethod("abm2")).eval
    AbstractClassDef("abstractClass6", Field("f2"), Constructor(Assign("f2", Value(2))), Method("abm1", Value(3)), Method("abm2", Union("set1","set2")), AbstractMethod("m3")).eval
    Implements("abstractClass6","I6").eval
    ClassDef("classC", Field("fa"), Constructor(Assign("fa", Value(10))), Method("m5", Value(5))).eval
    Extends("classC","abstractClass6").eval shouldEqual "Please implement all abstract methods."
  }
  behavior of "A Class implementing multiple interfaces"
  it should "ensure that a class implements 2 or more interfaces within a single Implements construct" in {
    InterfaceDecl("I7",AbstractMethod("abm1")).eval
    InterfaceDecl("I8",AbstractMethod("abm1")).eval
    InterfaceDecl("I9",AbstractMethod("abm3"),AbstractMethod("abm4")).eval
    ClassDef("classD", Field("f1"), Constructor(Assign("f1", Value(1))), Method("abm1", Value(1)), Method("abm2", Value("Text")), Method("abm3", Value(3)), Method("abm4", Union("set1","set2"))).eval
    Implements("classD","I7","I8", "I9" ).eval shouldEqual "Implementation successful!"
  }
}