package com.azad.tacocloud.tacos.data.dataJDBC;

import com.azad.tacocloud.tacos.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
