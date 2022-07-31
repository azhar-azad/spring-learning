package com.azad.hateoascrud.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("update Account a set a.balance = a.balance + ?1 where a.id = ?2")
    @Modifying
    @Transactional
    void deposit(double amount, Integer id);

    @Query("update Account a set a.balance = a.balance - ?1 where a.id = ?2")
    @Modifying
    @Transactional
    void withdraw(double amount, Integer id);
}
