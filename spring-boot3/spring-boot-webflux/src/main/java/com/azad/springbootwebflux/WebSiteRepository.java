package com.azad.springbootwebflux;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WebSiteRepository {

    private static final Map<Integer, WebSite> WEBSITES = new HashMap<>();
    static {
        WEBSITES.put(1, new WebSite(1, "https://google.com", "Google"));
        WEBSITES.put(2, new WebSite(2, "https://facebook.com", "Facebook"));
    }

    private static int ID_COUNTER = 3;

    public Flux<WebSite> findAll() {
        return Flux.fromIterable(WEBSITES.values());
    }

    public Mono<WebSite> findById(Integer id) {
        return Mono.just(WEBSITES.get(id));
    }

    public Mono<Void> delete(Integer id) {
        WEBSITES.remove(id);
        return Mono.empty();
    }

    public Mono<Void> add(Mono<WebSite> webSite) {
        return webSite.doOnNext(wb -> {
            Integer id = ID_COUNTER++;
            wb.setId(id);
            WEBSITES.put(id, wb);
        }).thenEmpty(Mono.empty());
    }

    public Mono<Void> update(Mono<WebSite> webSite) {
        return webSite.doOnNext(wb -> {
            WEBSITES.put(wb.getId(), wb);
        }).thenEmpty(Mono.empty());
    }
}
