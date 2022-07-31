package com.azad.hateoascrud.account;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    List<Account> listAll() {
        return repository.findAll();
    }

    Account get(Integer id) {
        return repository.findById(id).get();
    }

    Account save(Account account) {
        return repository.save(account);
    }

    Account deposit(double amount, Integer id) {
        repository.deposit(amount, id);
        return repository.findById(id).get();
    }

    Account withdraw(double amount, Integer id) {
        repository.deposit(amount, id);
        return repository.findById(id).get();
    }

    void delete(Integer id) {
        repository.deleteById(id);
    }
}
