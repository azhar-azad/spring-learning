package com.azad.tacocloud.tacos.web.api.reactive;

import com.azad.tacocloud.tacos.Ingredient;
import com.azad.tacocloud.tacos.Taco;
import com.azad.tacocloud.tacos.data.reactive.TacoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TacoControllerTest {

    @Test
    public void shouldReturnRecentTacos() {
        Taco[] tacos = {
                testTaco(1L), testTaco(2L), testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L), testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L), testTaco(11L), testTaco(12L)
        };
        Flux<Taco> tacoFlux = Flux.just(tacos);

        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient.bindToController(new TacoController(tacoRepo)).build();

        testClient.get().uri("/api/tacos?recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Taco 1")
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo("Taco 2")
                .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
                .jsonPath("$[11].name").isEqualTo("Taco 12")
                .jsonPath("$[13]").doesNotExist();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSaveATaco() {
        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);

        WebTestClient testClient = WebTestClient.bindToController(new TacoController(tacoRepo)).build();

        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(1L));
        Taco savedTaco = testTaco(1L);
        Flux<Taco> savedTacoMono = Flux.just(savedTaco);

        when(tacoRepo.saveAll(any(Mono.class))).thenReturn(savedTacoMono);

        testClient.post()
                .uri("/api/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class)
                .isEqualTo(savedTaco);
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(number != null ? number : 0L);
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }
}
