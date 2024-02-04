package com.azad.tacocloud.tacos.web.api.functional;

import com.azad.tacocloud.tacos.Taco;
import com.azad.tacocloud.tacos.data.reactive.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Autowired
    private TacoRepository tacoRepo;

    /*
    The routerFunction() method declares a RouterFunction<?> bean. It is created to handle GET requests for
    /api/tacos?recent and POST request for /api/tacos.
     */
    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/api/tacos").and(queryParam("recent", t -> t != null)),
                        this::recents)
                .andRoute(POST("/api/tacos"),
                        this::postTaco);
    }

    private Mono<ServerResponse> recents(ServerRequest request) {
        /*
        This method uses the injected TacoRepository to fetch a Flux<Taco>, from which it takes 12 items. It then
        wraps the Flux<Taco> in a Mono<ServerResponse> so that we can ensure that the response has an HTTP 200 (OK)
        status by calling ok() on the ServerResponse. It's important to understand that even though up to 12 tacos
        are returned, there is only one server response - that's why it is returned in a Mono and not a Flux.
        Internally, Spring will stream the Flux<Taco> to the client as a Flux.
         */
        return ServerResponse.ok()
                .body(tacoRepo.findAll().take(12), Taco.class);
    }

    private Mono<ServerResponse> postTaco(ServerRequest request) {
        /*
        This method extracts a Mono<Taco> from the body of the incoming ServerRequest. It then uses a series of
        flatMap() operations to save that taco to the TacoRepository and create a ServerResponse with an HTTP
        201 (CREATED) status code and the saved Taco object in the response body. The flatMap() operations are
        used to ensure that at each step in the flow, the result of the mapping is wrapped in a Mono, starting
        with a Mono<Taco> after the first flatMap() and ultimately ending with a Mono<ServerResponse> that is
        returned from the method.
         */
        return request.bodyToMono(Taco.class)
                .flatMap(taco -> tacoRepo.save(taco))
                .flatMap(savedTaco -> ServerResponse.created(URI.create(
                        "http://localhost:8080/api/tacos" + savedTaco.getId()))
                        .body(savedTaco, Taco.class));
    }


}
