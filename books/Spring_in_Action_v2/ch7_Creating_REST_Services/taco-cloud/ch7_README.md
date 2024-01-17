## Chapter 7: Creating REST Services
### Enabling Data-backed Services
Spring Data REST is another member of the Spring Data family that automatically 
creates REST APIs for repositories created by Spring Data. By doing little more 
than adding Spring Data REST to our build, we get an API with operations for each 
repository interface we've defined. 

To start using Spring Data REST, we can add the following dependency to our build: 
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```
By simply having the Spring Data REST starter in the build, the application gets 
autoconfiguration that enables automatic creation of a REST API for any repositories
that were created by Spring Data (including Spring Data JPA, Spring Data Mongo, and 
so on). To use this, we cannot have any controller class annotated with 
`@RestController`. 

After removing those classes, we should be able to perform GET requests for tacos, 
ingredients, orders, and users. For example, we can get a list of all ingredients
by making a GET request for /ingredients. Using curl, we might get something that 
looks like this (abridged to show only the first ingredient): 
```shell
$ curl localhost:8080/ingredients
```
```json
{
  "_embedded": {
    "ingredients": [
      {
        "name": "Flour Tortilla",
        "type": "WRAP",
        "_links": {
          "self": {
            "href": "http://localhost:8080/ingredients/FLTO"
          },
          "ingredient": {
            "href": "http://localhost:8080/ingredients/FLTO"
          }
        }
      },
      ...
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/ingredients"
    },
    "profile": {
      "href": "http://localhost:8080/profile/ingredients"
    }
  }
}
```
These hyperlinks are implementations of Hypermedia as the Engine of Application State,
or HATEOAS for short. A client consuming this API could (optionally) use these 
hyperlinks as a guide for navigating the API and performing the next request. 

Pretending to be a client of this API, we can also use curl to follow the self link
for the flour tortilla entry as follows: 
```shell
$ curl http://localhost:8080/ingredients/FLTO
```
```json
{
  "name" : "Flour Tortilla",
  "type" : "WRAP",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/ingredients/FLTO"
    },
    "ingredient" : {
      "href" : "http://localhost:8080/ingredients/FLTO"
    }
  }
}
```
One thing we might want to do is set a base path for the API so that its endpoints
are distinct and don't collide with any controllers we write. To adjust the base 
path for the API, we can set the `spring.data.rest.base-path` property as shown: 
```yaml
spring:
  data: 
    rest: 
      base-path: /data-api
```
This sets the base path for Spring Data REST endpoints to /data-api. The choice of 
/data-api ensures that endpoints exposed by Spring Data REST don't collide with any
other controllers, including those whose path begins with /api that we created 
earlier. Consequently, the ingredients endpoint is now /data-api/ingredients. Let's 
give this new base path a spin: 
```shell
$ curl http://localhost:8080/data-api/ingredients/FLTO
```
```json
{
  "name" : "Flour Tortilla",
  "type" : "WRAP",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/data-api/ingredients/FLTO"
    },
    "ingredient" : {
      "href" : "http://localhost:8080/data-api/ingredients/FLTO"
    }
  }
}
```

#### Adjusting resource paths and relation names
Let's try the following api call: 
```shell
$ curl http://localhost:8080/data-api/tacos
```
```json
{
    "timestamp": "2018-02-11T16:22:12.381+0000",
    "status": 404,
    "error": "Not Found",
    "message": "No message available",
    "path": "/api/tacos"
}
```
That didn't work quite as expected. As we have a Taco entity and a TacoRepository
interface, why doesn't Spring Data REST give us a /data-api/tacos endpoint? 

When creating endpoints for Spring Data repositories, Spring Data REST tries to 
pluralize the associated entity class. For the Ingredient entity, the endpoint is 
/data-api/ingredients. For the TacoOrder entity, it's /data-api/orders. So far, so
good. 

But sometimes, such as with "taco", it trips up on a word and the pluralize version 
isn't quite right. As it turns out, Spring Data REST pluralized "taco" as "tacoes", 
so to make a request for tacos, we must play along and request /data-api/tacoes, 
as shown: 
```shell
$ curl http://localhost:8080/data-api/tacoes
```
```json
{
  "_embedded": {
    "tacoes": [
      {
        "createdAt": "2024-01-16T08:19:00.746+00:00",
        "name": "Carnivore",
        "_links": {
          "self": {
            "href": "http://localhost:8080/data-api/tacoes/1"
          },
          "taco": {
            "href": "http://localhost:8080/data-api/tacoes/1"
          },
          "ingredients": {
            "href": "http://localhost:8080/data-api/tacoes/1/ingredients"
          }
        }
      },
      ...
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/data-api/tacoes?page=0&size=20"
    },
    "profile": {
      "href": "http://localhost:8080/data-api/profile/tacoes"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 3,
    "totalPages": 1,
    "number": 0
  }
}
```
Spring Data REST exposes a home resource that lists links for all exposed endpoints.
```shell
$ curl localhost:8080/api
```
```json
{
    "_links" : {
        "orders" : {
          "href" : "http://localhost:8080/data-api/orders"
        },
        "ingredients" : {
          "href" : "http://localhost:8080/data-api/ingredients"
        },
        "tacoes" : {
          "href" : "http://localhost:8080/data-api/tacoes{?page,size,sort}",
          "templated" : true
        },
        "users" : {
          "href" : "http://localhost:8080/data-api/users"
        },
        "profile" : {
          "href" : "http://localhost:8080/data-api/profile"
        }
    }
}
```
We don't have to accept this little quirk of Spring Data REST. By adding the 
following simple annotation to the Taco class, we can tweak both the relation 
name and the path:

```java
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@RestResource(rel = "tacos", path = "tacos")
public class Taco {
    // ...
}
```

#### Paging and sorting
To request the first page of tacos where the page size is 5, we can issue the 
following GET request: 
```shell
$ curl "localhost:8080/data-api/tacos?size=5"
```
Assuming there are more than five tacos to be seen, we can request the second page 
of tacos by adding the page parameter as follows: 
```shell
$ curl "localhost:8080/data-api/tacos?size=5&&page=1"
```
_The page parameter is zero-based, which means that asking for page 1 is actually
asking for the second page._

The sort parameter lets us sort the resulting list by any property of the entity.
For example, we need a way to fetch the 12 most recently created tacos for the UI
to display. We can do that by specifying the following mix of paging and sorting 
parameters: 
```shell
$ curl "localhost:8080/data-api/tacos?sort=createdAt,desc&page=0&size=12"
```
Here the sort parameter specifies that we should sort by the `createdDate` property
and that it should be sorted in descending order (so that the newest tacos are first).

### Consuming REST Services
A Spring application can consume a REST API with the following: 
- _RestTemplate_ - A straightforward, synchronous REST client provided by the core
Spring Framework. 
- _Traverson_ - A wrapper around Spring's `RestTemplate`, provided by the Spring 
HATEOAS, to enable a hyperlink-aware, synchronous REST client. Inspired from a 
JavaScript library of the same name. 
- _WebClient_ - A reactive, asynchronous REST client. 

Working with low-level HTTP libraries, the client needs to create a client instance
and a request object, execute the request, interpret the response, map the response
to domain objects, and handle any exceptions that may be thrown along the way. And 
all of this boilerplate is repeated, regardless of what HTTP request is sent. 

To avoid such boilerplate code, Spring provides `RestTemplate`. Just as `JdbcTemplate`
handles the ugly parts of working with JDBC, `RestTemplate` frees us from dealing 
with the tedium of consuming REST resources. 

`RestTemplate` defines 12 unique operations, each of which is overloaded, providing
a total of 41 methods. 
- `delete(...)` Performs an HTTP DELETE request on a resource at a specified URL.
- `exchange(...)` Executes a specified HTTP method against a URL, returning a 
`ResponseEntity` containing an object mapped from the response body. 
- `execute(...)` Executes a specified HTTP method against a URL, returning an object
mapped from the response body. 
- `getForEntity(...)` Sends an HTTP GET request, returning a `ResponseEntity` 
containing an object mapped from the response body. 
- `getForObject(...)` Sends an HTTP GET request, returning an object mapped from a 
response body. 
- `headForHeaders(...)` Sends an HTTP HEAD request, returning the HTTP headers for
the specified resource URL.
- `optionsForAllow(...)` Sends an HTTP OPTIONS request, returning the 'Allow' header
for the specified URL.
- `patchForObject(...)` Sends an HTTP PATCH request, returning the resulting object
mapped from the response body. 
- `postForEntity(...)` POSTs data to a URL, returning a `ResponseEntity` containing
an object mapped from the response body. 
- `postForLocation(...)` POSTs data to a URL, returning the URL of the newly 
created resource. 
- `postForObject(...)` POSTs data to a URL, returning an object mapped from the 
response body.
- `put(...)` PUTs resource data to the specified URL. 

Most of the methods above are overloaded into the following three method forms: 
- One accepts a `String` URL specification with URL parameters specified in a 
variable argument list. 
- One accepts a `String` URL specification with URL parameters specified in a 
`Map<String,String>`. 
- One accepts a `java.net.URI` as the URL specification, with no support for 
parameterized URLs. 

To use `RestTemplate`, we'll either need to create an instance at the point we 
need it, as follows:

```java
import org.springframework.web.client.RestTemplate;

RestTemplate rest = new RestTemplate();
```
or we can declare it as a bean and inject it where we need it, as shown:

```java
import org.springframework.context.annotation.Bean;

@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

#### GETting Resources
Suppose that we want to fetch an ingredient from the Taco Cloud API. For that, we 
can use `RestTemplate`'s `getForObject()` to fetch the ingredient.

```java
import com.azad.tacocloud.tacos.Ingredient;

public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject("http://localhost:8080/ingredients/{id}", 
            Ingredient.class, ingredientId);
}
```
Here we're using `getForObject()` variant that accepts a String URL and uses a 
variable list for URL variables. The `ingredientId` parameter passed into 
`getForObject()` is used to fill in the `{id}` placeholder in the given URL. 
Although there's only one URL variable in this example, it's important to know that
the variable parameters are assigned to the placeholders in the order that they're
given. 

The second parameter to `getForObject()` is the type that the response should be 
bound to. In this case, the response data should be deserialized into an Ingredient
object that will be returned. 

Alternatively, we can use a `Map` to specify the URL variables, as shown:

```java
import com.azad.tacocloud.tacos.Ingredient;

import java.util.HashMap;
import java.util.Map;

public Ingredient getIngredientById(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    return rest.getForObject("http://localhost:8080/ingredients/{id}", 
            Ingredient.class, urlVariables);
}
```
In this case, the value of `ingredientId` is mapped to a key of `id`. When the 
request is made, the `{id}` placeholder is replaced by the map entry whose key is id. 

Using a `URI` parameter is a bit more involved, requiring that we construct a `URI`
object before calling `getForObject()`. Otherwise, it's similar to both of the other
variants, as shown here:

```java
import com.azad.tacocloud.tacos.Ingredient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public Ingredient getIngredientById(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    URI url = UriComponentsBuilder
            .fromHttpUrl("http://localhost:8080/ingredients/{id}")
            .build(urlVariables);
    return rest.getForObject(url, Ingredient.class);
}
```
Here the `URI` object is defined from a String specification, and its placeholders 
filled in from entries in a `Map`, much like the previous variant of `getForObject()`. 
The `getForObject()` method is no-nonsense way of fetching a resource. But if the 
client needs more than the payload body, we may want to consider using `getForEntity()`.

`getForEntity()` works in a much the same way as `getForObject()`, but instead of 
returning a domain object that represents the response's payload, it returns a 
`ResponseEntity` object that wraps that domain object. The `ResponseEntity` gives 
access to additional response details, such as the response headers. 

For example, suppose that in addition to the ingredient data, we want to inspect 
the `Date` header from the response. With `getForEntity()` that becomes straightforward,
as shown in the following code:

```java
import com.azad.tacocloud.tacos.Ingredient;
import org.springframework.http.ResponseEntity;

public Ingredient getIngredientById(String ingredientId) {
    ResponseEntity<Ingredient> responseEntity = 
            rest.getForEntity("http://localhost:8080/ingredients/{id}", 
                    Ingredient.class, ingredientId);
    log.info("Fetched time: {}", responseEntity.getHeaders().getDate());
    return responseEntity.getBody();
}
```
The `getForEntity()` method is overloaded with the same parameters as `getForObject()`,
so we can provide the URL variables list parameter or call `getForEntity()` with a 
`URI` object. 

#### PUTting resources
For sending HTTP PUT requests, `RestTemplate` offers the `put()` method. All three
overloaded variants of `put()` accept and Object that is to be serialized and sent
to the given URL. As for the URL itself, it can be specified as a `URI` object or 
as a String. And like `getForObject()` and `getForEntity()`, the URL variables 
can be provided as either a variable argument list or as a `Map`. 

Suppose that we want to replace an ingredient resource with the data from a new 
Ingredient object. The following code should do the trick:

```java
import com.azad.tacocloud.tacos.Ingredient;

public void updateIngredient(Ingredient ingredient) {
    rest.put("http://localhost:8080/ingredients/{id}", 
            ingredient, ingredient.getId());
}
```
Here the URL is given as a String and has a placeholder that's substituted by the 
given Ingredient object's `id` property. The data to be sent is the Ingredient 
object itself. The `put()` method returns `void`, so there's nothing we need to 
do to handle a return value. 

#### DELETEing resources
Suppose that Taco Cloud no longer offers an ingredient and wants it completely 
removed as an option. To make that happen, we can call the `delete()` method from 
`RestTemplate` as follows:

```java
import com.azad.tacocloud.tacos.Ingredient;

public void deleteIngredient(Ingredient ingredient) {
    rest.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
}
```
Here only the URL (specified as a String) and a URL variable value are given to 
`delete()`. But as with the other `RestTemplate` methods, the URL could be 
specified as a `URI` object of the URL parameters given as a `Map`. 

#### POSTing resource data
Let's say that we add a new ingredient to the Taco Cloud menu. An HTTP POST request
to the .../ingredients endpoint with ingredient data in the request body will make 
that happen. `RestTemplate` has three ways of sending a POST request, each of which
has the same overloaded variants for specifying the URL. If we wanted to receive 
the newly created Ingredient resource after the POST request, we'd use `postForObject()`
like this:

```java
import com.azad.tacocloud.tacos.Ingredient;

public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject("http://localhost:8080/ingredients", 
            ingredient, Ingredient.class);
}
```
This variant of the `postForObject()` method takes a String URL specification, the 
object to be posted to the server, and the domain type that the response body should
be bound to. Although we aren't taking advantage of it in this case, a fourth 
parameter could be a `Map` of the URL variable or a variable list of parameters to 
substitute into the URL. 

If our client has more need for the location of the newly created resource, then 
we can call `postForLocation()` instead, as shown here:

```java
import com.azad.tacocloud.tacos.Ingredient;

import java.net.URI;

public URI createIngredient(Ingredient ingredient) {
    return rest.postForLocation("http://localhost:8080/ingredients", ingredient);
}
```
Notice that `postForLocation()` works much like `postForObject()`, with the 
exception that it returns a `URI` of the newly created resource instead of the
resource object itself. The `URI` returned is derived from the response's `Location`
header. In the off chance that we need both the location and response payload, we
can call `postForEntity()` like so:

```java
import com.azad.tacocloud.tacos.Ingredient;
import org.springframework.http.ResponseEntity;

public Ingredient createIngredient(Ingredient ingredient) {
    ResponseEntity<Ingredient> responseEntity = 
            rest.postForEntity("http://localhost:8080/ingredients", 
                    ingredient, Ingredient.class);
    log.info("New resource created at {}", responseEntity.getHeaders().getLocation());
    return responseEntity.getBody();
}
```
Although the methods of `RestTemplate` differ in their purpose, they're quite similar
in how they're used. This makes it easy to become proficient with `RestTemplate` and
use it in our client code. 

### Chapter Summary
- REST endpoints can be created with Spring MVC, with controllers that follow the 
same programming model as browser-target controllers. 
- Controller handler methods can either be annotated with `@ResponseBody` or return
`ResponseEntity` objects to bypass the model and view and write data directly to 
the response body. 
- The `@RestController` annotation simplifies REST controllers, eliminating the 
need to use `@ResponseBody` on handler methods. 
- Spring Data repositories can automatically be exposed as REST APIs using Spring
Data REST. 


