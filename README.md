![logo](app/src/main/resources/kleicreator_wide.png)
# KleiCreator
[![Java CI with Gradle](https://github.com/DecDuck/kleicreator/actions/workflows/gradle.yml/badge.svg)](https://github.com/DecDuck/kleicreator/actions/workflows/gradle.yml)

KleiCreator is a GUI tool for creating mods for the games made by Klei Entertainment. It uses a the Java Runtime Environment and cross-platform libraries to enable mods to be made on any platform.  

## About
KleiCreator runs in the JVM (Java virtual machine), and maintains cross-platform compatibility. Additionally, it supports plugins, allowing anyone to extend its functionality without needing to modify and distribute their own version of KleiCreator.

## Plugins
As with a lot of Java applications, plugins are compiled and packaged as `.jar` files. Currently, there is no official documentation for the process of creating plugins, but it can be reverse-engineered by looking through the source code of the SDK. Documentation, however, is on the way.

## Using
KleiCreator can be downloaded from any release, usually called `app-X.X.X.jar`, with an accompanying SDK. These releases are generally quite stable, and automatically check for updates that are newer than themselves. Additionally, you can download versions compiled per commit by GitHub Actions by clicking on the tick next to the commit and then downloading the artifacts. 

### Building from Source
Some users may prefer to compile the project themselves rather download a binary. To do so:
#### Step 1
Install Java JDK version 16 or later. You can find Oracle's archives here: [Java SE 16 Archive Downloads](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html), or use your (Linux) system's package manager.
#### Step 2
Download or clone the source code. If you have Git installed:
```
git clone https://github.com/DecDuck/kleicreator.git
```
Alternatively, click the 'Code' button and 'Download ZIP'. Then extract it to an easily accessible place. 
#### Step 3
Open a console and go to the project folder. Then run the following commands:
```
gradlew jar
```
(If you're on a Unix-based system, you may need a preceding './')
#### Step 4
The relevant jars should be in `app/build/libs/app-X.X.X.jar` and `sdk/build/libs/sdk-X.X.X.jar`.

## Contributing
KleiCreator is open-source, and as with every OSS project, every contribution counts. If you're contributing by writing code and implementing features, fork this repository, commit your changes, then create a Pull Request to get it merged into the root codebase. If you're contributing through testing, create issues with the problem you've found and hopefully a developer will take a look at them and fix the issue.

### Styles
If you're making any assets for KleiCreator, the colours used in the logo are:
 - Gray: 5b5b5b
 - Orange: ffb400
