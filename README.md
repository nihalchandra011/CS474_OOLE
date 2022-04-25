## CS474_OOLE
# Homework 5 - Set Theory
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

NOTE - Homework 5 has been implemented in the Scala file located at ```src/main/scala/SetTheory5.scala``` and ```src/main/scala/Optimizer.scala```.

**Constructs**<br>
In this homework, instead of creating new constructs, the earlier constructs were modified in order to incroporate the changes and allow for partial evaluation and optimization functions. The following constructs have been mainly changed:

- **Variable(varName)**
  - Checks if the variable exists in the binding.
  - If yes, then returns its value
  - Or else, the entire expression Variable(varName) is returned as it is, instead of giving an error.

- **Check(operator1, operator2)**
  - Checks if both the operators exist in the binding.
  - If yes, then compares them and returns a boolean value. 
  - Or else, if either of the operators do not exist the entire expression, the expression is returns based after partial evaluation of whatever binding exists, instead of giving an error.

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
