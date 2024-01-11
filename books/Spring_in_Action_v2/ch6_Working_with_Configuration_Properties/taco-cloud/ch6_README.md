## Chapter 6: Working with Configuration Properties
### Fine-tuning Autoconfiguration
- _Bean wiring_: Configuration that declares application components to be created
as beans in the Spring context and how they should be injected into each other.
- _Property injection_: Configuration that sets values on beans in the Spring 
application context. 

In Spring's XML and Java configuration, these two types of configurations are 
often declared explicitly in the same place. For example, let us consider the 
following `@Bean` method that declares a DataSource for an embedded H2 database:
```java

```