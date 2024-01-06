package com.azad.tacocloud.tacos.data;

import com.azad.tacocloud.tacos.TacoOrder;

/***
 * In our design, TacoOrder and Taco are part of an aggregate in which TacoOrder is the aggregate root. In other words,
 * Taco objects don't exist outside the context of a TacoOrder. So for now, we only need to define a repository to
 * persist TacoOrder objects, and in turn, Taco objects along with them.
 */
public interface OrderRepository {

    TacoOrder save(TacoOrder order);
}
