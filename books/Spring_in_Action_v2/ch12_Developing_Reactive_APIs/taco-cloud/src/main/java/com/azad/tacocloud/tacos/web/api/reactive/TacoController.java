package com.azad.tacocloud.tacos.web.api.reactive;

import com.azad.tacocloud.tacos.Taco;
import com.azad.tacocloud.tacos.data.OrderRepository;
import com.azad.tacocloud.tacos.data.reactive.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/reactive/tacos", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(params = "recent")
    public Flux<Taco> recentTacos() {
        return tacoRepo.findAll().take(12);
    }

    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") Long id) {
        return tacoRepo.findById(id);
    }

    /*
    The postTaco() accepts a Mono<Taco> and calls the repository's saveAll() method, which accepts any implementation of
    Reactive Streams Publisher, including Mono or Flux. By accepting a Mono<Taco> as input, the method is invoked
    immediately without waiting for the Taco to be resolved from the request body. And because the repository is also
    reactive, it'll accept a Mono and immediately return a Flux<Taco>, from which we call next() and return the
    resulting Mono<Taco>... all before the request is even processed!
     */
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
        /*
        The saveAll() method returns a Flux<Taco>, but because we started with a Mono, we know there's at most one Taco
        that wil be published by the Flux. We can therefore call next() obtain a Mono<Taco> that will be returned.
         */
        return tacoRepo.saveAll(tacoMono).next();
    }

    /*
    Alternatively, we could also implement postTaco() like the following. This approach flips things around so that the
    tacoMono is the driver of the action. The Taco contained within tacoMono is handed to the repository's save() method
    via flatMap(), resulting in a new Mono<Taco> that is returned.
     */
    public Mono<Taco> postTaco2(@RequestBody Mono<Taco> tacoMono) {
        return tacoMono.flatMap(entity -> tacoRepo.save(entity));
    }
}
