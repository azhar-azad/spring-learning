## Chapter 2: Getting Started with Spring
### Project tasks done: 
    - Created an inital project structure using the Spring Initializr. 
    - Wrote a controller class to handle the home page request. 
    - Defined a view template to render the home page. 
    - Wrote a simple test class to prove our work. 
### What is Spring doing behind the scenes
- In the `pom.xml` file, we declared a dependency on the _Web_ and _Thymeleaf_
starters. These two dependencies transitively brought in a handful of other 
dependencies, including the following: 
  - Spring's MVC framework 
  - Embedded Tomcat
  - Thymeleaf and the Thymeleaf layout dialect. 
- It also brought Spring Boot's autoconfiguration library along for the ride. 
When the application starts, Spring Boot autoconfiguration detects those
libraries and automatically performs the following tasks: 
  - Configures the beans in the Spring application context to enable Spring MVC
  - Configures the embedded Tomcat server in the Spring application context
  - Configures a Thymeleaf view resolver for rendering Spring MVC views with 
  Thymeleaf templates
### Chapter Summary
- Spring aims to make developer challenges easy, like creating web applications, 
working with databases, securing applications, and microservices. 
- Spring Boot builds on top of Spring to make Spring even easier with simplified 
dependency management, automatic configuration, and runtime insights. 
- Spring applications can be initialized using the Spring Initializr, which is 
web-based and supported natively in most Java development environments. 
- The components, commonly referred to as beans, in a Spring application context
can be declared explicitly with Java or XML, discovered by component scanning,
or automatically configured with Spring Boot autoconfiguration. 