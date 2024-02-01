package com.azad.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class PerformingLogicOperationsReactiveTypesTest {

    /*
    Suppose we want to know that every String published by a Flux contains the letter a or the
    letter k.
     */
    @Test
    public void all() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
        /*
        Here we check for letter a. The all() operation is applied to the source Flux, resulting
        in a Mono of type Boolean. In this case, all the animal names contain the letter a,
        so true is emitted from the resulting Mono.
         */
        StepVerifier.create(hasAMono)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
        /*
        Here, the resulting Mono will emit false because not all the animal names contain a k.
         */
        StepVerifier.create(hasKMono)
                .expectNext(false)
                .verifyComplete();
    }

    /*
    Rather than perform an all-or-nothing check, maybe we're satisfied if at least one entry
    matches. In that case, the any() operation is what we want.
     */
    @Test
    public void any() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasTMono = animalFlux.any(a -> a.contains("t"));
        /*
        Here, we see that the resulting Mono emits true, because at least one animal name has the
        letter t (specifically, elephant).
         */
        StepVerifier.create(hasTMono)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
        /*
        Here, the resulting Mono emits false, because none of the animal names contain z.
         */
        StepVerifier.create(hasZMono)
                .expectNext(false)
                .verifyComplete();
    }
}
