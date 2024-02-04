package com.azad.tacocloud.tacos.data.reactive;

import com.azad.tacocloud.tacos.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
