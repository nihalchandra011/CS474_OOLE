## CS474_OOLE
# Homework 4 - Set Theory
**Nihal Chandra**<br>
**UIN: 674916217**<br><br>
This project implements a Domain-Specific Language (DSL) using Scala for writing and evaluating set operations of Set Theory. Using this DSL, users can describe and evaluate binary operations on sets using variables and scopes where elements of the sets can be objects of any type.

**Execution Environment**<br>
This language is programmed in Scala (Version 3.1.0) and executed in the IntelliJ IDEA integrated Development Environment (Version 2021.3.2 - Ultimate Edition). Some outputs might vary if there is a change in the versions. Make sure you have Scala, IntelliJ, SBT and ScalaTest installed before implementing the language.

**Package Installation**<br>
To use this language, clone this GitHub Repository to you local machine and execute in IntelliJ by implementing the following steps:
- There are several ways to clone to your local machine. You can use HTTPS or SSH, amongst other options. Let’s use HTTPS as it can be the simplest option. 
- Copy the GitHub link of this repository.
- When you first open the IntelliJ IDEA page, you see a screen with the  option 'Get from VCS'. Click on that to see a box where you can enter the Github URL. 
- Choose the directory where you want to load the project and click  on the 'Clone' button.
- Voila! The project is loaded into your IntelliJ. You can now run the program and execute the test cases.

NOTE - Homework 4 has been implemented in the Scala file located at ```src/main/scala/SetTheory4.scala```.

**Class Constructs**<br>
In addition to the constructs implemented in Homeworks 1,2 and 3, the following constructs have been used to implement Branching using if-else and Exception Handling using try-catch blocks.

- **IF(condition: Operations, thenClause: Set[Operations], elseClause: Set[Operations])**<br>
  - This construct defines the use of if-else branching similar to OOP Languages.
  - Follows lazy evaluation, i.e,  if the condition is evaluated to true then only thenClause is evaluated and elseClause is not.
  - The thenClause and elseClause are given as a Set of operations.
  - The Operations can be any of the set operations or another If-else construct.
  - Nested If constructs are also implemented.

- **Scope(scopeName: String, expressions: Operations*)**<br>
  - Defines the code region within which the bindings are active.
  - Although it has been implemented before, a definitive construct has been created here to be used whenever required.
  - The 'scopeName' is used to access the given scope.

- **ExceptionClassDef(exceptionClassName: String, reason:Operations)**<br>
  - Creates a class 'exceptionClassName' and pushes it to a stack to be accessed later.
  - A mapping of the 'reason' is created that associates the exceptionClassName with the reason.
  - Returns the mapping created between the exceptionClassName and reason.

- **ThrowException(exceptionClassName: Operations, exceptionDefinition: Operations)**<br>
  - Throws an exception whenever encountered.
  - Adds the value of the exception reason to the mapping mentioned previously.

- **CatchException(exceptionClassName: String, tryExpressions: Set[Operations], catchExpressions: Set[Operations])**<br>
  - This construct handles the major part of try-catch block. 
  - The 'exceptionClassName' is used to access and invoke the Exception Class whenever required.
  - The 'tryExpressions' and 'catchExpressions' contain a set of expressions which could be operations or if-else constructs.
  - The try block executes even if there is no exception thrown. But corresponding catch is obviously not evaluated in this case.

- **Catch(catchTreatment: Operations*)**<br>
  - Evaluates the expressions present inside the Catch block of the Try-Catch.
  - This block only executes if an error is thrown.
  
**Not Implemented**<br>
The following concept has not been implemented in this language yet:
- Nested try-catch block execution.

**Testing**<br>
Using IntelliJ:<br>
The tests are present under ```src/test/scala/SetTesting4.scala```. Right-click and run the program to check the test cases. 

Using Terminal/Command Prompt:<br>
To run the SBT Tests from the Command Line, do the following:
  - Open the Command Prompt.
  - Navigate to the directory holding the current project.
  - Type ```sbt new scala/scalatest-example.g8``` and hit Enter.
  - Now run ```sbt test```.
  - You will be prompted to enter a template name. Give it an interesting name and hit Enter. 
  - Optionally, you can also do an ```sbt clean compile test``` from the command prompt to check the execution.
  - This will evaluate all the test cases provided in the Project.
  - If all of them execute, you will get a green success message.
