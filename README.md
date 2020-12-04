# Sylk    [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)[ ![Download](https://api.bintray.com/packages/penguinz/Sylk/Sylk/images/download.svg) ](https://bintray.com/penguinz/Sylk/Sylk/_latestVersion)

Sylk is a 2D game engine written in Java and is currently in development. With goals of being able to write a good looking and optimized game in Sylk, it is constantly being worked on a iterated upon.

## Setting Up

You can use Sylk to write your own games and deploy them as a bundled executable jar file for others to play and can be integrated within your maven or gradle project.

Maven:

    <repository>
      <id>central</id>
      <name>bintray</name>
      <url>https://jcenter.bintray.com</url>
    </repository>
    
    <dependency>
      <groupId>dev.penguinz</groupId>
      <artifactId>Sylk</artifactId>
      <version>0.1.0</version>
      <type>pom</type>
    </dependency>
<br>
Gradle:

    repositories {
        jcenter()
    }
    
    dependencies {
        implementation "dev.penguinz:Sylk:VERSION"
    }
