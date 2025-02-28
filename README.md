# Mars rover

## Problem 

### Description

We have an [initial java program](./InitialLegacyProgram.java) that someone made a long time ago.

We want to refactor it and create a new one based on it.

Develop an api that moves a rover around on a grid using a command line client.

- Command line should be 
- You will have to provide the map size
- You will have to provide the number of obstacles and their position
- You will have to provide the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
- The rover receives a character array of commands.
- Implement commands that move the rover forward/backward (f,b).
- Implement commands that turn the rover left/right (l,r).
- Implement wrapping from one edge of the grid to another. (planets are spheres after all)
- Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point and reports the obstacle.

### How to
We want to see your changes but not make them public. In order to do so create a new repo on bitbucket based on this project, make all the changes and give us access to see them ;).

We need the commit list in order to check the evolution, naming and changes you made during the process.
  
## Getting Started

### Prerequisites

As we are using gradle wrapper the project requires only a Java JDK or JRE version 8 or higher to be installed.
To check run `java -version`.

### Running the tests
 
```bash
./gradlew test
open build/reports/tests/test/index.html
```
Jococo has been added to check the overall coverage of the project, run:
```bash
./gradlew jacocoTestReport
open build/jacocoHtml/index.html
```
The Plugin seems to does not work pretty accurate with kotlin, some tested branches are not detected and
the *plugin gives you some false negatives*. 

### Building and packaging

```bash
./gradlew shadowJar
```

### Running locally

Just run the fat-jar after building (previous section):
```bash
java -jar build/libs/mars-rover-kata-wp-all.jar --help
```
Working sample 
`java -jar build/libs/mars-rover-kata-wp-all.jar ffrfflfbf --initial-point="5 5" --direction=N --map-size="20 20"`
Or
```bash
 ./gradlew run --args='ffrfflfbf --initial-point="5 5" --direction=N --map-size="20 20"'
```

## Approach

In this exercise I have tried to stick in two main things:
- Refactor the java [legacy code](./InitialLegacyProgram.java).
- Use TDD to guide me through the design and cover all the code with tests.

The code is split in two main layers:

- cli: Command line interface framework, parsers and formatters
- core: Domain layer, all the logic

All fully tested, even the cli 🤘

The code should be self explanatory and you will see that I have a lot of influence from functional programming, 
(immutability, lambdas, monads ...), but I have tried to make it as clean as possible.

Usually I keep and eye in the big picture, but in this kata I have been following YAGNI principle, so in theory the code
shouldn't be over-engineered.


## Tech stack

- Language: Kotlin
- 3rd party libs:
    - Testing:
        - JUnit5
        - Mockito 2
        - Assertk
        - Jococo
    - Functional Programming support: Arrow
    - CLI: clikt
    
## Improvements

After only two days working in that exercise I have leaved it with some pending improvements:

- Grid validation, the size (negative values) and the obstacles (are they inside the grid?) are not validated
- Better integration testing of the cli
- Logging
- Add a usecase/service layer to orchestrate and decouple presentation from domain 
- CI 
- Remove gradle warnings



