## CS474_OOLE
# Homework 2 - Set Theory
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
- Voila! The porject is loaded into your IntelliJ. You can now run the program and execute the test cases.

**Class Constructs**<br>
The following datatypes have been used to implement classes, objects, methods and their interaction including inheritance.

- **ClassDef(className: String, classContents: Expression)**<br>
  - Creates a blueprint of the class and adds it to the mapping.
  - If there are any field values, initializes it to 0.
  - Constructors and Methods are not evaluted just yet. They are executed when the they are called using the corresponding class object.

- **Field(fieldName: String)**<br>
  - The field is defined here.
  - Its is given a default value 0.

- **Constructor(className: String, expressions: Expression)**<br>
  - This is invoked when a new object is created.
  - Initializes the values of the instance variables.
  - Can execute multiple number of initializations.

- **Method(methodName: String, methodsExp: Expression)**<br>
  - Implements the operations present inside the method.
  - Returns the result of the last operation.
  - Can take in and execute multiple operations inside it.

- **case NewObject(className: String, objName: String)**<br>
  - Creates a new object of the given className.
  - Initializes the values defined in the class constructor.
  - The given language currently works with only two objects at a time.
  - Returns the objName after creation.
 
- **InvokeMethod(objName: String, methodName: String)**<br>
  - Invokes the method 'methodName' present in the class of the object 'objName' passed as parameter.
  - Returns the result of the method after execution.

- **Extends(subClassName: String, superClassName: String)**<br>
  - Implements the conventional 'extends' keyword.
  - This expression gets in all the methods and variables of the superclass into the subclass.
  - Prevents Multiple Inheritance.

**Testing**<br>
Using IntelliJ:<br>
The tests are present under ```src/test/scala/SetTesting2.scala```. Right-click and run the program to check the test cases. 

Using Terminal/Command Prompt:<br>
To run the SBT Tests from the Command Line, do the following:
  - Open the Command Prompt.
  - Navigate to the directory holding the current project.
  - Type ```sbt new scala/scalatest-example.g8``` and hit Enter.
  - Now run ```sbt test```.
  - You will be prompted to enter a template name. Give it an interesting name and hit Enter. 
  - This will evaluate all the test cases provided in the Project.
  - If all of them execute, you will get a green success message.


