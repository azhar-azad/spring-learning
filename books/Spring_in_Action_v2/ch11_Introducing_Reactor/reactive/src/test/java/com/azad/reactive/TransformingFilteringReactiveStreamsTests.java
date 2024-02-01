package com.azad.reactive;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TransformingFilteringReactiveStreamsTests {

    /*
    One of the most basic ways of filtering data as it flows from a Flux is to simply disregard the first so many
    entries. The skip() operation does exactly that.
     */
    @Test
    public void skipAFew() {
        /*
        Given a Flux with several entries, the skip() operation wil create a new Flux that skips over a specified
        number of items before emitting the remaining items from the source Flux. We have a Flux of five String
        items. Calling skip(3) on that Flux produces a new Flux that skips over the first three items and publishes
        only the last two items.
         */
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .skip(3);

        StepVerifier.create(countFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    /*
    Maybe we don't want to skip a specific number of items but instead need to skip the first so many items until some
    duration has passed.
     */
    @Test
    public void skipAFewSeconds() {
        /*
        An alternate form of the skip() operation, produces a Flux that waits until some specified time has passed
        before emitting items from the source Flux. This skip() method creates a Flux that waits 4 seconds before
        emitting any values.
         */
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));

        /*
        Because that Flux was created from a Flux that has a 1-second delay between items (using delayElements()),
        only the last two items will be emitted.
         */
        StepVerifier.create(countFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    /*
    We've already seen an example of the take() operation, but in light of the skip() operation, take() can be thought
    of as the opposite of skip().
     */
    @Test
    public void take() {
        /*
        take() emits only the first so many items.
         */
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia")
                .take(3);

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    /*
    Like skip(), take() also has an alternative form that's based on a duration rather than an item count.
     */
    @Test
    public void takeForAWhile() {
        /*
        This take() emits as many items as it can in the first 3.5 seconds after subscription.
         */
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    /*
    The skip() and take() operations can be thought of as filter operations where the filter criteria are based on a
    count or a duration. For more general-purpose filtering of Flux values, we'll find the filter() operation quite
    useful. Given a Predicate that decides whether an item will pass through the Flux, the filter() operation lets us
    selectively publish based on whether criteria we want.
     */
    @Test
    public void filter() {
        /*
        filter() is given a Predicate as a lambda that accepts only String values that don't have any spaces.
         */
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia")
                .filter(np -> !np.contains(" "));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Zion", "Acadia")
                .verifyComplete();
    }

    /*
    Perhaps the filtering we need is to filter out any items that we've already received. The distinct() operation
    results in a Flux that publishes only items from the source Flux that haven't already been published.
     */
    @Test
    public void distinct() {
        Flux<String> animalFlux = Flux.just("dog", "cat", "bird", "dog", "bird", "anteater")
                .distinct();

        StepVerifier.create(animalFlux)
                .expectNext("dog", "cat", "bird", "anteater")
                .verifyComplete();
    }

    /*
    One of the most common operations we'll use on either a Flux or a Mono is to transform published items to some
    other form or type. Reactor's types offer map() and flatMap() operations for that purpose.

    The map() operation creates a Flux that simply performs a transformation as prescribed by a given Function on each
    object it receives before republishing it.
     */
    @Test
    public void map() {
        /*
        A Flux of String values representing basketball players is mapped to a new Flux of Player objects. Although the
        Flux created with just() carried String objects, the Flux resulting from map() carries Player objects.
         */
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(n -> {
                    String[] split = n.split("\\s");
                    return new Player(split[0], split[1]);
                });
        /*
        What's important to understand about map() is that the mapping is performed synchronously, as each item is
        published by the source Flux.
         */

        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }

    /*
    If we want to perform the mapping asynchronously, we should consider the flatMap() operation. flatMap() maps each
    object to a new Mono or Flux. The results of the Mono and Flux are flattened into a new resulting Flux. When used
    along with subscribeOn(), flatMap() can unleash the asynchronous power of Reactor's types.
     */
    @Test
    public void flatMap() {
        /*
        flatMap() is given a lambda Function that transforms the incoming String into a Mono of type String. A map()
        operation is then applied to the Mono to transform the String into a Player. After the String is mapped to a
        Player on each internal Flux, they are published into a single Flux returned by flatMap(), thus completing the
        flattening of the results.
        If we stopped right there (without subscribeOn() call), the resulting Flux would carry Player objects, produced
        synchronously in the same order as with the map() example. But the last thing we do with the Mono is call the
        subscribeOn() method to indicate that each subscription should take place in a parallel thread. Consequently,
        the mapping operations for multiple incoming String objects can be performed asynchronously and in parallel.
         */
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                        .map(p -> {
                            String[] split = p.split("\\s");
                            return new Player(split[0], split[1]);
                        })
                        .subscribeOn(Schedulers.parallel())
                );

        List<Player> playerList = Arrays.asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr"));

        /*
        The upside to using flatMap() and subscribeOn() is that we can increase the throughput of the stream by
        splitting the work across multiple parallel threads. But because the work is being done in parallel, with no
        guarantees on which will finish first, there's no way to know the order of items emitted in the resulting Flux.
        Therefore, StepVerifier is able to verify only that each item emitted exists in the expected list of Player
        objects and that there will be three such items before the Flux completes.
         */
        StepVerifier.create(playerFlux)
                .expectNextMatches(p -> playerList.contains(p))
                .expectNextMatches(p -> playerList.contains(p))
                .expectNextMatches(p -> playerList.contains(p))
                .verifyComplete();
    }

    /*
    In the course of processing the data flowing through a Flux, we might find it helpful to break the stream of data
    into bite-size chunks. The buffer() operation can help with that.
     */
    @Test
    public void buffer() {
        /*
        Given a Flux of String values, each containing the name of a fruit, we can create a new Flux of List collections,
        where each List has no more than a specified number of elements.
         */
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        /*
        The Flux of String elements is buffered into a new Flux of List collections containing no
        more than three items each. Consequently, the original Flux that emits five String values
        will be converted to a Flux that emits two List collections, one containing three fruits
        and the other with two fruits.
         */
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier.create(bufferedFlux)
                .expectNext(Arrays.asList("apple", "orange", "banana"))
                .expectNext(Arrays.asList("kiwi", "strawberry"))
                .verifyComplete();
    }

    /*
    Buffering values from a reactive Flux into nonreactive List collections seems counterproductive.
    but when we combine buffer() with flatMap(), it enables each of the List collections to be
    processed in parallel.
     */
    @Test
    public void bufferAndFlatMap() {
        /*
        We still buffer a Flux of five String values into a new Flux of List collections. This
        takes each List buffer and creates a new Flux from its elements, and then applies a map()
        operation on it. Consequently, each buffered List is further processed in parallel in
        individual threads.
        To prove that it works, we've also included a log() operation to be applied to each
        sub-Flux. The log() operation simply logs all Reactive Streams events, so that we can
        see what's really happening.
         */
        Flux.just("apple", "orange", "kiwi", "strawberry")
                .buffer(3)
                .flatMap(x -> Flux.fromIterable(x)
                        .map(String::toUpperCase)
                        .subscribeOn(Schedulers.parallel())
                        .log()
                ).subscribe();
        /*
        As the log entries clearly show, the fruits in the first buffer (apple, orange, and
        banana) are handled in the parallel-1 thread. Meanwhile, the fruits in the second buffer
        (kiwi and strawberry) are processed in the parallel-2 thread. As is apparent by the fact
        that the log entries from each buffer are woven together, the two buffers are processed
        parallel.
        If for some reason, we need to collect everything that a Flux emits into a List, we can
        call buffer() with no arguments as follows:
            Flux<List<String>> bufferedFlux = fruitFlux.buffer();
         */
    }

    /*
    Using the buffer() method with no argument results in a new Flux that emits a List that contains
    all the items published by the source Flux. We can achieve the same thing with the collectList()
    operation.
     */
    @Test
    public void collectList() {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        Mono<List<String>> fruitListMono = fruitFlux.collectList();

        StepVerifier.create(fruitListMono)
                .expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
                .verifyComplete();
    }

    /*
    An even more interesting way of collecting items emitted by a Flux is to collect them into a
    Map.
     */
    @Test
    public void collectMap() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");

        /*
        The collectMap() operation results in a Mono that publishes a Map that's populated with
        entries whose key is calculated by a given Function.
         */
        Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));

        /*
        The source Flux emits the names of a handful of animals. From that Flux, we use collectMap()
        to create a new Mono that emits a Map, where the key value is determined by the first letter
        of the animal name and the value is the animal name itself. In the event that two animal
        names start with the same letter (as with elephant and eagle or koala and kangaroo), the last
        entry flowing through the stream overrides any earlier entries.
         */
        StepVerifier.create(animalMapMono)
                .expectNextMatches(map -> map.size() == 3
                        && map.get('a').equals("aardvark")
                        && map.get('e').equals("eagle")
                        && map.get('k').equals("kangaroo"))
                .verifyComplete();
    }

    @Data
    private static class Player {
        private final String firstName;
        private final String lastName;
    }
}
