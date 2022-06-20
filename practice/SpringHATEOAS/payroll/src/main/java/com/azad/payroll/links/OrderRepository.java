package com.azad.payroll.links;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select count(*) from customer_order", nativeQuery = true)
    long count();
}
