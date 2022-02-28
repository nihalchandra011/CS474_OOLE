## CS474_OOLE
# Homework 1 - Set Theory
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

**Set Operations**
<br>In this language, the following operations have been implemented:<br>
- **Value(input: Any)**<br>
  - Returns the value that is passed into it.
  - The value passed is of Any type.

- **Check(setName: String, element: SetExpression)**<br>
  - Checks if an element is contained in a set or not. 
  - Takes in two parameters - the Set Name and the element to be stored in it (could be an integer, a Double type or a string). 
  - It returns 'true' if the element is found or else 'false'.

- **Assign(varName: String, value: SetExpression)**<br>
  - Assigns the value obtained from the SetExpression to variable 'varName'. 
  - It will create a new one if the given variable name contained in op1 does not exist. 
  - Returns the value after assigning it.

- **Insert(varName: String, element: SetExpression)**<br> 
  - Inserts the element obtained from the SetExpression into the variable 'varName'.  
  - Does not allow you (throws an error) to add elements to variables that do not exist. 
  - Returns the value(s) stored in 'varName' after insertion of the element.

- **Delete(varName: String, element: SetExpression)**<br> 
  - Deletes an element obtained from the SetExpression, from the set 'varName'.
  - Ignores the deletion if the set does not exist itself.
  - Returns the set after deletion.
   
- **Union(set1: String, set2: String)**<br> 
  - Performs the union of the two sets 'set1' and 'set2'.
  - In other words, it returns all the elements present in both set1 and set2.
  - Returns the set obtained after performing the union operation.

- **Intersection(set1: String, set2: String)**<br> 
  - Performs the union of the two sets 'set1' and 'set2'.
  - In other words, it returns only the common elements present in both set1 and set2.
  - Returns the set obtained after performing the union operation.

- **SetDifference(set1: String, set2: String)**<br> 
  - Performs the set difference of the two sets 'set1' and 'set2'.
  - In other words, it returns the elements present set1 but not in set2.
  - Returns the set obtained after performing the difference operation.

- **SymmetricDifference(set1: String, set2: String)**<br> 
  - Performs the symmetric difference of the two sets 'set1' and 'set2'.
  - In other words, it returns the elements  that are a member of exactly one of set1 and set2 (elements which are in one of the sets, but not in both).
  - Returns the set obtained after finding their Symmetric Difference .

- **CartesianProduct(set1: String, set2: String)**<br> 
  - Performs the union of the two sets 'set1' and 'set2'.
  - In other words, it returns the elements that are all possible ordered pairs (a, b), where a is a member of set1 and b is a member of set2.
  - Returns the set obtained after finding the Cartesian Product of both the sets.

- **assignMacro(macroName: String, macroOp: SetExpression)**<br> 
  - Assigns the SetExpression contained in 'marcoOp' to the 'macroName' so that it can be easily referenced later through the marcoName.

- **resolveMacro(macroName: String)**<br> 
  - Resolves the 'macroName' and implements its corresponding SetExpression.
  - Returns the result obtained after performing the corresponding operation stored as 'macroName'.

Testing
Using IntelliJ:
The tests are present under src/test/scala/SetTesting2.scala. Right-click and run the program to check the test cases.

Using Terminal/Command Prompt:
To run the SBT Tests from the Command Line, do the following:

- Open the Command Prompt.
- Navigate to the directory holding the current project.
- Type sbt new scala/scalatest-example.g8 and hit Enter.
- Now run sbt test.
- You will be prompted to enter a template name. Give it an interesting name and hit Enter.
- This will evaluate all the test cases provided in the Project.
- If all of them execute, you will get a green success message.
