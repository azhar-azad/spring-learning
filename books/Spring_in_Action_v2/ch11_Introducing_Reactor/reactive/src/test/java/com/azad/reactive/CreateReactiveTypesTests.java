package com.azad.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class CreateReactiveTypesTests {

    /*
	Creating Flux from objects
	If we have one or more objects from which we'd like to create a Flux or Mono, we can use the static just()
	method on Flux or Mono to create a reactive type whose data is driven by those objects.
	 */
    @Test
    public void createAFlux_just() {
		/*
		Creates a Flux from five String objects.
		 */
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		/*
		At this point, the Flux has been created, but it has no subscribers. Without any subscribers, data won't flow.
		Subscribing to a reactive type is how we turn on the flow of data. To add a subscriber, we can call the
		subscribe() method on the Flux.
		 */
        fruitFlux.subscribe(f -> System.out.println("Here's some fruit: " + f));

		/*
		Printing the entries from a Flux or Mono to the console is a good way to see the reactive type in action.
		But a better way to actually test a Flux or a Mono is to use Reactor's StepVerifier. Given a Flux or Mono,
		StepVerifier subscribes to the reactive type and then applies assertions against the data as it flows through
		the stream, finally verifying that the stream completes as expected.
		 */
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    /*
    Creating from collections
    A Flux can also be created from an array, Iterable, or Java Stream. To create a Flux from an array, we can call the
    static fromArray() method, passing in the source array.
     */
    @Test
    public void createAFlux_fromArray() {
        String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    /*
    If we need to create a Flux from a List, Set, or any other implementation of Iterable, we can pass it into the
    static fromIterable() method.
     */
    @Test
    public void createAFlux_fromIterable() {
        List<String> fruitList = new ArrayList<>();
        fruitList.add("Apple");
        fruitList.add("Orange");
        fruitList.add("Grape");
        fruitList.add("Banana");
        fruitList.add("Strawberry");

        Flux<String> fruitFlux = Flux.fromIterable(fruitList);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    /*
    If we happen to have a Java Stream that we'd like to use as the source for a Flux, fromStream() is the method we'll
    use.
     */
    @Test
    public void createAFlux_fromStream() {
        Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");

        Flux<String> fruitFlux = Flux.fromStream(fruitStream);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    /*
    Generating Flux Data
    Sometimes we don't have any data to work with and just need Flux to act as a counter, emitting a number that
    increments with each new value. To create a counter Flux, we can use the static range() method.
     */
    @Test
    public void createAFlux_range() {
		/*
		The range Flux is created with a starting value of 1 and an ending value of 5.
		 */
        Flux<Integer> intervalFlux = Flux.range(1, 5);

        StepVerifier.create(intervalFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    /*
    Another Flux-creation method that's similar to range() is interval(). Like the range() method, interval() creates a
    Flux that emits an incrementing value. But what makes interval() special is that instead of we giving it a starting
    and ending value, we specify a duration or how often a value should be emitted.
     */
    @Test
    public void createAFlux_interval() {
		/*
		To create an interval Flux that emits a value every second, we can use the static interval() method. Because
		 interval() isn't given a maximum value, it will potentially run forever. Therefore, we also use the take()
		 operation to limit the result to the first five entries.
		 */
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }
}
