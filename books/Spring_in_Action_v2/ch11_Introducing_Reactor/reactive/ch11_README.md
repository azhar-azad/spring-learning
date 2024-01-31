## Chapter 11: Introducing Reactor
### Understanding Reactive Programming
**Java Streams vs Reactive Streams**
- Both provide a functional API for working with data. 
- Both share many of the same operations. 
- Java streams are typically synchronous and work with a finite set of data. 
They're essentially a means of iterating over a collection with functions. 
- Reactive streams support asynchronous processing of datasets of any size, 
including infinite datasets. They process data in real time, as it becomes 
available, with back pressure to avoid overwhelming their consumers. 
- JDK 9's Flow APIs correspond to Reactive Streams. The `Flow.Publisher`, 
`Flow.Subscriber`, `Flow.Subscription`, and `Flow.Processor` types in JDK 9 map
directly to `Publisher`, `Subscriber`, `Subscription`, and `Processor` in 
Reactive Streams. That said, JDK 9's Flow APIs are not an actual implementation of
Reactive Streams. 

The Reactive Streams specification can be summed up by four interface definitions:
`Publisher`, `Subscriber`, `Subscription`, and `Processor`. A `Publisher` produces
data that it sends to a `Subscriber` per a `Subscription`. The `Publisher` 
interface declares a single method, `subscribe()`, through which a `Subscriber` can
subscribe to the `Publisher`, as shown here:

```java
import org.reactivestreams.Subscriber;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
```
Once a `Subscriber` has subscribed, it can receive events from the `Publisher`. 
Those events are sent via methods on the `Subscriber` interface as follows:

```java
import org.reactivestreams.Subscription;

public interface Subscriber<T> {
    void onSubscribe(Subscription sub);
    void onNext(T item);
    void onError(Throwable ex);
    void onComplete();
}
```
The first event that the `Subscriber` will receive is through a call to `onSubscribe()`.
When the `Publisher` calls `onSubscribe()`, it passes a `Subscription` object to the
`Subscriber`. It's through the `Subscription` that the `Subscriber` can manage its 
subscription, as shown next: 
```java
public interface Subscription {
    void request(long n);
    void cancel();
}
```
The `Subscriber` can call `request()` to request that data be sent, or it can call 
`cancel()` to indicate that it's no longer interested in receiving data and is 
canceling the subscription. When calling `request()`, the `Subscriber` passes in a 
long value to indicate how many data items it's willing to accept. 

As for the `Processor` interface, it's a combination of `Subscriber` and `Publisher`,
as shown here:

```java
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {}
```
As a `Subscriber`, a `Processor` will receive data and process it in some way. Then
it wil switch hats and act as a `Publisher` to publish the results to its subscribers. 

In a nutshell, we can build up a data processing pipeline that starts with a `Publisher`, 
pumps data through zero or more `Processor`, and then drops the final results off to a 
`Subscriber`. 

### Getting Started with Reactor
Reactive programming means building a pipeline through which data will flow, rather 
than describe a set of steps to be taken in the imperative programming style. As data
passes through the pipeline, it can be altered or used in some way. 

For example, suppose we want to take a person's name, change all of the letters to 
uppercase, use it to create a greeting message, and then finally print it. In an 
imperative programming model, the code would look something like this: 
```java
String name = "Craig";
String capitalName = name.toUpperCase();
String greeting = "Hello, " + capitalName + "!";
System.out.println(greeting);
```
In the imperative model, each line of code performs a step, one right after the other,
and definitely in the same thread. Each step blocks the executing thread from moving 
to the next step until complete. 

In contrast, functional, reactive code could be achieved the same thing as this:

```java
Mono.just("Craig")
        .map(n -> n.toUpperCase())
        .map(cn -> "Hello, " + cn + "!")
        .subscribe(System.out::println);
```
The `Mono` in the example is one of Reactor's two core types. `Flux` is the other. 
Both are implementations of Reactive Stream's `Publisher`. A `Flux` represents a 
pipeline of zero, one, or many (potentially infinite) data items. A `Mono` is a 
specialized reactive type that's optimized for when the dataset is known to have no 
more than one data item. 

#### Adding Reactor Dependencies
To get started with Reactor, we need to add the following dependency to the project 
build: 
```xml
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
</dependency>
```
Reactor also provides some great testing support. 
```xml
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Applying Common Reactive Operations
`Flux` and `Mono` are the most essential building blocks provided by Reactor, 
and the operations those two reactive types offer are the mortar that binds 
them together to create pipelines through which data can flow. `Flux` and `Mono`
offer more than 500 operations, which can be loosely categorized as follows: 
- Creation
- Combination
- Transformation
- Logic

_Look for code and instructions on the Test classes_

#### Creating reactive types
- from objects: `just()`
- from collections: `fromArray()`, `fromIterable()`, `fromStream()`
- generating data: `range(start, end)`, `interval(duration)`

#### Combining reactive types
- Merging reactive types: `mergeWith()`, `zippedFlux()`, `zip()`
- Selecting the first reactive type to publish: `firstWithSignal()`

#### Transforming and filtering reactive streams
- Filtering data - `skip()`, `take()`, `filter()`, `distinct()`
- Mapping reactive data - `map()`, `flatMap()`
- Buffering data - `buffer()`, `collectList()`, `collectMap()`

#### Performing logic operations on reactive types
- `all()`, `any()`

### Chapter Summary
- Reactive programming involves creating pipelines through which data flows. 
- The Reactive Streams specification defines four types: `Publisher`, `Subscriber`, `Subscription`,
and `Processor` (which is a combination of `Publisher` and `Subscriber`).
- Project Reactor implements Reactive Streams and abstracts stream definitions into two primary
types, `Flux` and `Mono`, each of which offers several hundred operations. 
- Spring leverages Reactor to create reactive controllers, repositories, REST clients, and other 
reactive framework support. 
