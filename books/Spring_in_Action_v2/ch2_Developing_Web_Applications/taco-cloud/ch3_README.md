## Chapter 2: Developing Web Applications
### Chapter Summary
- Spring offers a powerful web framework called Spring MVC that can be used to 
develop the web frontend for a Spring application. 
- Spring MVC is annotation-based, enabling the declaration of request-handling
methods with annotations such as `@RequestMapping`, `@GetMapping`, and 
`@PostMapping`. 
- Most request-handling methods conclude by returning the logical name of a 
view, such as a Thymeleaf template, to which the request (along with any model
data) is forwarded. 
- Spring MVC supports validation through the JavaBean Validation API and 
implementations of the Validation API such as Hibernate Validator. 
- View controllers can be registered with `addViewController` in a 
`WebMvcConfigurer` class to handle HTTP GET requests for which no model data 
or processing is required. 
- In addition to Thymeleaf, Spring supports a verity of view options, including
FreeMarker, Groovy templates, and Mustache. 