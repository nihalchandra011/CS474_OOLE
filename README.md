## CS474_OOLE
# Homework 3 - Set Theory
**Nihal Chandra**<br>
**UIN: 674916217**<br><br>
This project implements a Domain-Specific Language (DSL) using Scala for writing and evaluating set operations of Set Theory. Using this DSL, users can describe and evaluate binary operations on sets using variables and scopes where elements of the sets can be objects of any type.

**Execution Environment**<br>
This language is programmed in Scala (Version 3.1.0) and executed in the IntelliJ IDEA integrated Development Environment (Version 2021.3.2 - Ultimate Edition). Some outputs might vary if there is a change in the versions. Make sure you have Scala, IntelliJ, SBT and ScalaTest installed before implementing the language.

**Package Installation**<br>
To use this language, clone this GitHub Repository to you local machine and execute in IntelliJ by implementing the following steps:
- There are several ways to clone to your local machine. You can use HTTPS or SSH, amongst other options. Let’s use HTTPS as it can be the simplest option. 
- Copy the github link of this repository.
- When you first open the IntelliJ IDEA page, you see a screen with the  option 'Get from VCS'. Click on that to see a box where you can enter the Github URL. 
- Choose the directory where you want to load the project and click  on the 'Clone' button.
- Voila! The project is loaded into your IntelliJ. You can now run the program and execute the test cases.

NOTE - Homework 3 has been implemented in the Scala file located at ```src/test/scala/SetTheory3.scala```.

**Class Constructs**<br>
In addition to the constructs implemented in Homeworks 1 and 2, the following datatypes have been used to implement abstract classes, abstract methods and interfaces and their interaction including inheritance.

- **AbstractClassDef(abstractClassName: String, abstractClassContents: Operations)**<br>
  - This construct defines an abstract class.
  - It should contain atleast 1 abstract method. (Will give an error message otherwise)
  - In this construct, it is only created and the corresponding mappings are added.
  - Returns the Class -> Method mappings after definition.

- **AbstractMethod(methodName: String)**<br>
  - Since it is abstract, its definition is not given.
  - In this construct, only its mappings are added and returned.

- **InterfaceDecl(interfaceName: String, abstractMethods: Operations)**<br>
  - An interface is created with the name 'interfaceName'.
  - It contains only abstract method.
  - The default method has not been implemented.
  - The abstract methods are defined when this interface is implemented.
  - Returns mapping of interface to abstract methods.

- **Implements(subClassName: String, interfaceName: String)**<br>
  - The class 'subClassName' implemnts the 'interfaceName' interface.
  - The parameters represent the following: subClassName - class, abstract class or interface | interfaceName: Interface
  - Can implement multiple interfaces.

While building the language, the following questions helped better understand the implementation:
- **Can a class/interface inherit from itself?**
- **Ans.** No, Circular Definitions are not allowed, i.e., if there is a chain of inheritance from the class/interface A1 to the class/interface AN then once encountered in the chain of inheritance a class/interface AK cannot appear any more below itself in this inheritance chain.
<br>Error: Cyclic inheritance: class className extends itself
<br>This has been implemented in this language in the following manner:
<br>```if (subClassName == superClassName) return "Error: Cyclic inheritance: " + subClassName + " extends itself."```
<br>Here we check if the subClass and superClass are the same, i.e, the class calls itself. If it does, an error message is returned.

- **Can an interface inherit from an abstract class with all pure methods?** 
- **Ans.** No, an interface cannot inherit from an abstract class with all pure methods.
<br> In this implementation, this has been ensured by the following code snippet:
<br> ```if (InterfaceStack.contains(subClassName) && abstractMethodMapping.contains(superClassName))
              "Interface cannot inherit from a pure abstract class."```
<br>Here, we are checking if the 'subClassName' is an interface and the 'superClassName' is an abstract class.

- **Can an interface implement another interface?** 
- **Ans.** No, an interface cannot implement another interface, because if any interface is implemented then its methods must be defined and interface never has the definition of any method.
<br> In this implementation, this has been ensured by the following code snippet:
<br> ```if (InterfaceStack.contains(subClassName) && InterfaceStack.contains(name))
              return "An interface cannot implement another interface.```
<br>Here, we are checking if both the 'subClassName'and the 'superClassName' is are an interface. If yes, an error, an error message is given.

- **Can a class implement two or more different interfaces that declare methods with exactly the same signatures?** 
- **Ans.** Yes, it is possible for a class to inherit multiple methods with override-equivalent signatures. (JLS 8.4.8.4 Inheriting Methods with Override-Equivalent Signatures) If a type implements two interfaces, and each interface define a method that has identical signature, then in effect there is only one method, and they are not distinguishable. If, say, the two methods have conflicting return types, then it will be a compilation error.
<br>Consider the following snippet: <br>
InterfaceDecl("I1",AbstractMethod("abm1"))<br>
InterfaceDec2("I6",AbstractMethod("abm1"))<br>
Implements("subClass","I1","I2")<br>

Here, both the interfaces have identical methods with the same signature, and on implementing them, the subClass effectively implements only a single method.

- **Can an abstract class inherit from another abstract class and implement interfaces where all interfaces and the abstract class have methods with the same signatures?**
- **Ans** An abstract class can inherit from another abstract class and as already explained the interfaces and abstract methods have the same signature as the return type is not specified. Therefore, in this implementation, we can declare methods with the same name, then the abstract class will override the interface methods. 

- **Can an abstract class implement interfaces?** 
- **Ans.** Yes,	like any other class an abstract class can also implement an interface. Only difference being since it is an abstract class it is not forced to implement all the abstract methods of the interface. However, the class extending the abstract class must implement all the abstract methods of the abstract class as well as the interface which is implemented by the abstract class or shall declare itself abstract too.

- **Can a class implement two or more interfaces that have methods whose signatures differ only in return types?** 
- **Ans.** No, it gives a compile time error. (JLS Example 8.1.5-3. Implementing Methods of a Superinterface) because a class cannot have multiple methods with the same signature and different primitive return types.

- **Can an abstract class inherit from a concrete class?** 
- **Ans.** Yes, an abstract class can inherit from a concrete class. This has been incorporated into this implementation.

- **Can an abstract class/interface be instantiated as anonymous concrete classes?** 
- **Ans.** No you cannot instantiate an interface or abstract class. But you can instantiate an anonymous class that implements/extends the interface or abstract class without defining a class object.

**Not Implemented**<br>
The following concepts have not been implewmented in this language yet:
- Nested Interfaces
- Nested Abstract Classses
- Access Modifiers- public, private and protected

**Testing**<br>
Using IntelliJ:<br>
The tests are present under ```src/test/scala/SetTesting3.scala```. Right-click and run the program to check the test cases. 

Using Terminal/Command Prompt:<br>
To run the SBT Tests from the Command Line, do the following:
  - Open the Command Prompt.
  - Navigate to the directory holding the current project.
  - Type ```sbt new scala/scalatest-example.g8``` and hit Enter.
  - Now run ```sbt test```.
  - You will be prompted to enter a template name. Give it an interesting name and hit Enter. 
  - This will evaluate all the test cases provided in the Project.
  - If all of them execute, you will get a green success message.
