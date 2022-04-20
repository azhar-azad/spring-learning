/**
 * Repository methods are composed of a verb, an optional subject, the word By, 
 * and a predicate. In the case of findByDeliveryZip(), 
 * the verb is find and the predicate is DeliveryZip ; the subject isn’t 
 * specified and is implied to be a TacoOrder.
 * */

package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

//	TacoOrder save(TacoOrder order);
}
