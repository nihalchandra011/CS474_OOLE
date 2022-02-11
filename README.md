# CS474_OOLE
**Homework 1 - Set Theory**

This project implements a Domain-Specific Language (DSL) using Scala for writing and evaluating set operations of Set Theory. Using this DSL, users can describe and evaluate binary operations on sets using variables and scopes where elements of the sets can be objects of any type.

**Set Operations**<br>
In this language, the following operations have been impplemented:
- Check if an element is present in a set or not
- Assign values to objects
- Insert elements into a set
- Delete elements from a set.
- Union of the sets A and B, denoted A ∪ B, is the set of all objects that are a member of A, or B, or both.
- Intersection of the sets A and B, denoted A ∩ B, is the set of all objects that are members of both A and B.
- Set difference of U and A, denoted U \ A, is the set of all members of U that are not members of A.
- Symmetric difference of sets A and B, denoted A ⊖ B, is the set of all objects that are a member of exactly one of A and B (elements which are in one of the sets, but not in both, that is, (A ∪ B) \ (A ∩ B) or (A \ B) ∪ (B \ A)). For example, for the sets {1, 2, 3} and {2, 3, 4}, the symmetric difference set is {1, 4}. 
- Cartesian product of A and B, denoted A × B, is the set whose members are all possible ordered pairs (a, b), where a is a member of A and b is a member of B.
