package com.azad.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

@SpringBootTest
public class CombineReactiveTypesTests {

    /*
    Suppose we have two Flux streams and need to create a single resulting Flux that will produce
    data as it becomes available from either of the upstream Flux streams. To merge one Flux with
    another, we can use the mergeWith() operation.
     */
    @Test
    public void mergeFluxes() {
        /*
        We have a Flux whose values are the names of TV and movie characters.
         */
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500));
        /*
        We have a second Flux whose values are the names of foods that those characters enjoy
        eating.
         */
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")
                .delayElements(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));
        /*
        Normally, a Flux will publish data as quickly as it possibly can. Therefore, we use a
        delayElements() operation on both of the created Flux streams to slow them down a little -
        emitting an entry only every 500 ms. Furthermore, so that the food Flux starts streaming
        after the character Flux, we apply a delaySubscription() operation to the food Flux so
        that it won't emit any data until 250 ms have passed following a subscription.
         */

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("Garfield")
                .expectNext("Lasagna")
                .expectNext("Kojak")
                .expectNext("Lollipops")
                .expectNext("Barbossa")
                .expectNext("Apples")
                .verifyComplete();
    }

    /*
    Because mergeWith() can't guarantee a perfect back and forth between its sources, we may want
    to consider the zip() operation instead. When two Flux objects are zipped together, it results
    in a new Flux that produces a tuple of items, where the tuple contains one item from each source
    Flux.
     */
    @Test
    public void zipFluxes() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");

        /*
        Unlike mergeWith(), the zip() operation is a static creation operation.
         */
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);
        /*
        The created Flux has a perfect alignment between characters and their favourite foods. Each
        item emitted from the zipped Flux is a Tuple2 (a container object that carries two other
        objects) containing items from each source Flux, in the order that they're published.
         */

        StepVerifier.create(zippedFlux)
                .expectNextMatches(p ->
                        p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
                .expectNextMatches(p ->
                        p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
                .expectNextMatches(p ->
                        p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
                .verifyComplete();
    }

    /*
    If we'd rather not work with a Tuple2 and would rather work with some other type, we can provide
    a Function to zip() that produces any object we'd like, given the two items. The following test
    method shows how to zip the character Flux with the food Flux so that it results in a Flux of
    String objects.
     */
    @Test
    public void zipFluxesToObject() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");

        /*
        The Function given to zip() (given here as a lambda) simply concatenates the two items into
        a sentence to be emitted by the zipped Flux.
         */
        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);

        StepVerifier.create(zippedFlux)
                .expectNext("Garfield eats Lasagna")
                .expectNext("Kojak eats Lollipops")
                .expectNext("Barbossa eats Apples")
                .verifyComplete();
    }

    /*
    Suppose we have two Flux objects, and rather than merge them together, we merely want to create
    a new Flux that emits the values from the first Flux that produces a value. The following test
    method creates a fast Flux and a slow Flux (where "slow" means that it will not publish an
    item until 100 ms after subscription).
     */
    @Test
    public void firstWithSignalFlux() {
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

        /*
        Using firstWithSignal(), it creates a new Flux that will publish values only from the
        first source Flux to publish a value.
         */
        Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);
        /*
        Because the slow Flux won't publish any values until 100 ms after the fast Flux has started
        publishing, the newly created Flux will simply ignore the slow Flux and publish values only
        from the fast Flux.
         */

        StepVerifier.create(firstFlux)
                .expectNext("hare")
                .expectNext("cheetah")
                .expectNext("squirrel")
                .verifyComplete();
    }
}
