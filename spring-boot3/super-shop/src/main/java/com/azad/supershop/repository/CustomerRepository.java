package com.azad.supershop.repository;

import com.azad.supershop.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByPhone(String phone);

    @Query(value = "SELECT sum(c.totalDue) from CustomerEntity c")
    Optional<Double> findSumByTotalDue();

    @Query(value = "SELECT MAX(c.lifetimeBuyAmount) FROM CustomerEntity c")
    Optional<Double> findMaxLifetimeBuyAmount();

    @Query(value = "SELECT MIN(c.lifetimeBuyAmount) FROM CustomerEntity c")
    Optional<Double> findMinLifetimeBuyAmount();

    @Query(value = "SELECT MAX(c.totalTransactionCount) FROM CustomerEntity c")
    Optional<Long> findMaxTotalTransactionCount();

    @Query(value = "SELECT MIN(c.totalTransactionCount) FROM CustomerEntity c")
    Optional<Long> findMinTotalTransactionCount();

    @Query(value = "SELECT MIN(c.totalDue) FROM CustomerEntity c")
    Optional<Double> findMaxTotalDue();

    @Query(value = "SELECT MIN(c.totalDue) FROM CustomerEntity c")
    Optional<Double> findMinTotalDue();
}
