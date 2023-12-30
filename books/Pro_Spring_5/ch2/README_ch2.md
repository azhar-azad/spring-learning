## Chapter 2: Getting Started
#### Building the Simple Hello World Application
See `HelloWorld` class.

Problems with the `HelloWorld` class: 
- What if we want to change the message? What if we want to output 
the message differently? - we need to change the source code. 

A better solution is to externalize the message content and read it in 
at runtime, perhaps from the command-line arguments shown in the 
`HelloWorldCommandLine` class. This class accomplishes what we want - we 
can now change the message without changing the code. 

Problems with the `HelloWorldCommandLine` class: 
- The component responsible for rendering the message is also 
responsible for obtaining the message.
- We still cannot change the renderer easily; doing so means changing 
the class that launches the application. 

If we take this application a step further, a better solution is to 
refactor the rendering and message retrieval logic into separate 
components. See `MessageProvider` and `MessageRenderer` interface.
The code here is fairly simple. We instantiate instances of 
`HelloWorldMessageProvider` and `StandardOutMessageRenderer`, although 
the declared types are `MessageProvider` and `MessageRenderer`, 
respectively. This is because we need to interact only with the 
methods provided by the interface in the programming logic, and 
`HelloWorldMessageProvider` and `StandardOutMessageRenderer` already
implemented those interfaces, respectively. Then, we pass 
`MessageProvider` to `MessageRenderer` and invoke 
`MessageRenderer.render()`. If we compile and run this program, 
we get the expected “Hello World!”

Problems: 
- Changing the implementation of either the `MessageRenderer` or 
`MessageProvider` interface means a change to the code. 

To get around this, we can create a simple factory class that reads 
the implementation class names from a properties file and instantiates
them on behalf of the application. See `MessageSupportFactory` class. 

To make use of the new implementation, you must modify the main 
method again. See the `HelloWorldDecoupledWithFactory` class. 


#### Refactoring with Spring
The final example shown earlier met the goals laid out for the sample 
application, but there are still problems with it. The first problem 
is that we had to write a lot of glue code to piece the application
together, while at the same time keeping the components loosely 
coupled. The second problem is that we still had to provide the 
implementation of MessageRenderer with an instance of MessageProvider
manually. We can solve both of these problems by using Spring.
See `HelloWroldSpringDI` class. 

In the previous code snippet, you can see that the main() method 
obtains an instance of `ClassPathXmlApplicationContext` (the application
configuration information is loaded from the file 
`spring/app-context.xml` in the project’s classpath), typed as 
`ApplicationContext`, and from this, it obtains the `MessageRenderer` 
instances by using the `ApplicationContext.getBean()` method.


#### Spring Configuration Using Annotations
Starting with Spring 3.0, XML configuration files are no longer 
necessary when developing a Spring application. They can be replaced 
with annotations and configuration classes. Configuration classes are
Java classes annotated with `@Configuration` that contain bean 
definitions (methods annotated with `@Bean`) or are configured 
themselves to identify bean definitions in the application by
annotating them with `@ComponentScanning`. 
See `HelloWorldConfiguration` class. 

The `main()` method has to be modified to replace 
`ClassPathXmlApplicationContext` with another `ApplicationContext` 
implementation that knows how to read bean definitions from 
configuration classes. That class is `AnnotationConfigApplicationContext`
See `HelloWorldSpringAnnotated` class.


#### Summary
In this chapter, we presented you with all the background information you 
need to get up and running with Spring. We showed you how to get started
with Spring through dependency management systems and the current 
development version directly from GitHub. We described how Spring 
is packaged and the dependencies you need for each of Spring’s 
features. Using this information, you can make informed decisions 
about which of the Spring JAR files your application needs and which 
dependencies you need to distribute with your application. Spring’s 
documentation, guides, and test suite provide Spring users with an 
ideal base from which to start their Spring development, so we took 
some time to investigate what is made available by Spring. Finally, 
we presented an example of how, using Spring DI, it is possible to 
make the traditional Hello World a loosely coupled, extendable 
message-rendering application. The important thing to realize is that
we only scratched the surface of Spring DI in this chapter, and we 
barely made a dent in Spring as a whole. In the next chapter, we 
take look at IoC and DI in Spring.
