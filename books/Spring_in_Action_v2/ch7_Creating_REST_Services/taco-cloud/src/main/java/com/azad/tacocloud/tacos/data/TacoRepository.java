package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.Taco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
