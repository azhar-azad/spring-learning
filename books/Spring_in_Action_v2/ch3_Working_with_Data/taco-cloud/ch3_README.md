## Chapter 3: Working with Data
### Defining a Schema and Preloading Data
Aside from the Ingredient table, we're also going to need some tables that hold 
order and design information.
    
    Taco_Order: Holds essential order details. 
    Taco: Holds essential information about a taco design. 
    Ingredient_Ref: Contains one or more rows for each row in Taco, mapping the taco to 
                    the ingredients for that taco. 
    Ingredient: Holds ingredient information.
### Preloading Data with CommandLineRunner
Spring Boot offers two useful interfaces for executing logic when an application
starts up: `CommandLineRunner` and `ApplicationRunner`. Both are functional 
interfaces that require that a single `run()` method be implemented. When the
application starts up, any beans in the application context that implement 
`CommandLineRunner` or `ApplicationRunner` will have their `run()` methods 
invoked after the application context and all beans are wired up, but before
anything else happens. This provides a convenient place for data to be loaded
into the database. 

Here's how we can create a data-loading `CommandLineRunner` bean: 
```java
@Bean
public CommandLineRunner dataLoader(IngredientRepository repo) {
    return args -> {
        repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        // ...
    };
}
```
Alternatively, here's how we can create a data-loading `ApplicationRunner` bean:

```java
@Bean
public ApplicationRunner dataLoader(IngredientRepository repo) {
    return args -> {
        repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        // ...
    };
}

```
The key difference between those two runners is in the parameter passed to 
the respective `run()` methods. `CommandLineRunner` accepts a `String` 
vararg, which is a raw representation of arguments passed on the command 
line. But `ApplicationRunner` accepts a `ApplicationArguments` parameter
that offers methods for accessing the arguments as parsed components of the 
command line. 
### Adding Spring Data JPA to the Project
Spring Data JPA is available to Spring Boot applications with the JPA 
starter. This starter dependency not only brings in Spring Data JPA but 
also transitively includes Hibernate as the JPA implementation, as shown: 
```xml
<dependendies>
    <!-- ... -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <!-- ... -->
</dependendies>
```
If we want to use a different JPA implementation, then we'll need to, at 
least, exclude Hibernate dependency and include the JPA library of our 
choice. For example, to use EclipseLink instead of Hibernate, we'll need to 
alter the build as follows: 
```xml
<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.hibernate</groupId>
                <artifactId>hibernate-core</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.eclipse.persistence</groupId>
        <artifactId>org.eclipse.persistence.jpa</artifactId>
        <version>2.7.6</version>
    </dependency>
    <!-- ... -->
</dependencies>
```
### Chapter Summary
