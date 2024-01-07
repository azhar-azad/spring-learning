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
### Customizing JPA Repositories
When generating the repository implementation, Spring Data examines each 
method in the repository interface, parse the method name, and attempt to 
understand the method's purpose in the context of the persisted object. 

In the case of `TacoOrder` entity, if we need to fetch all the orders 
delivered to a given ZIP code, we can write the method as:

```java
List<TacoOrder> findByDeliveryZip(String deliveryZip);
```
Spring Data knows that this method is intended to find Orders, because we've
parameterized `CrudRepository` with `TacoOrder`. The method name,
`findByDeliveryZip()`, makes it clear that this method should find all 
`TacoOrder` entities by matching their `deliveryZip` property with the value
passed in as a parameter to the method. 

Spring Data can handle even more interesting method names as well. Repository
methods are composed of a _verb_, an optional _subject_, the word _By_, and a
_predicate_. In the case of `findByDeliveryZip()`, the verb is _find_ and the 
predicate is _DeliveryZip_; the subject isn't specified and is implied to 
be a `TacoOrder`. 

Let's consider another, more complex example. Suppose that we need to query 
for all orders delivered to a given ZIP code within a given date range. In 
that case, the following method, when added to `OrderRepository`, might 
prove useful:

```java
List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
```
As we can see, the verb is _read_. Spring Data also understands _find_, 
_read_, and _get_ as synonymous for fetching one or more entities. Alternatively, 
we can also use _count_ as verb if we want the method to return only an `int`
with the count of matching entities. The subject is optional and, we can give 
any words there, Spring Data will ignore the subject. 

The predicate refers to two `TacoOrder` properties: `deliveryZip` and 
`placedAt`. The `deliveryZip` property must be equal to the value passed 
into the first parameter of the method. The keyword `Between` indicates that 
the value of `deliveryZip` must fall between the values passed into the last 
two parameters of the method. 

In addition to an implicit `Equals` operation and the `Between` operation, 
Spring Data method signatures can also include any of the following operations: 
- IsAfter, After, IsGreaterThan, GreaterThan
- IsGreaterThanEqual, GreaterThanEqual
- IsBefore, Before, IsLessThan, LessThan
- IsLessThanEqual, LessThanEqual
- IsBetween, Between
- IsNull, Null
- IsNotNull, NotNull
- IsIn, In
- IsNotIn, NotIn
- IsStartingWith, StartingWith, StartsWith
- IsEndingWith, EndingWith, EndsWith
- IsContaining, Containing, Contains
- IsLike, Like
- IsNotLike, NotLike
- IsTrue, True
- IsFalse, False
- Is, Equals
- IsNot, Not
- IgnoringCase, IgnoresCase

As alternatives for IgnoringCase and IgnoresCase, we can place either 
`AllIgnoringCase` or `AllIgnoresCase` to ignore case for all String 
comparisons. 

Finally, we can also place `OrderBy` at the end of the method name to sort 
the results by a specified column. For example, to order by `deliveryTo`
property, we can use the following code:

```java
List<TacoOrder> findByDeliveryCityOrderByDeliveryTo(String city);
```

Although, we can use any queries without any naming convention and this 
can be useful on a very complex queries. For example

```java
import org.springframework.data.jpa.repository.Query;

@Query("Order o where o.deliveryCity='Seattle'")
List<TacoOrder> readOrdersDeliveredInSeattle();
```

### Chapter Summary
- Spring's `JdbcTemplate` greatly simplifies working with JDBC. 
- `PreparedStatementCreator` and `KeyHolder` can be used together when we 
need to know the value of a database-generated ID. 
- Spring Data JDBC and Spring Data JPA make working with relational data as 
easy as writing a repository interface. 