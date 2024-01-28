## Chapter 10: Integrating Spring
Spring Integration is a ready-to-use implementation of many of the integration
patterns that are catalogued in _Enterprise Integration_Patterns_ by Gregor 
Hohpe and Bobby Woolf. Each pattern is implemented as a component through 
which messages ferry data in a pipeline. Using Spring configuration, we can 
assemble these components into a pipeline through which data flows. 

### Declaring a Simple Integration Flow
Spring integration enables the creating of integration flows through which 
an application can receive or send data to some resource external to the 
application itself. 

One such resource that an application may integrate with is the filesystem.
To get our feet wet with Spring Integration, we're going to create an 
integration flow that writes data to the filesystem. To get started, we need
to add Spring Integration to our project build. 
```xml
<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-integration</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.integration</groupId>
        <artifactId>spring-integration-file</artifactId>
    </dependency>
    <!-- ... -->
</dependencies>
```
The first dependency is the Spring Boot starter for Spring Integration. This 
dependency is essential to developing a Spring Integration flow. The second
dependency is for Spring Integration's file endpoint module that offers the 
ability to ingest files from the filesystem into an integration flow and/or
to write data from a flow to the filesystem. 

Next we need to create a way for the application to send data into an 
integration flow so that it can be written to a file. To do that, we'll create 
a gateway interface. 


### Chapter Summary

