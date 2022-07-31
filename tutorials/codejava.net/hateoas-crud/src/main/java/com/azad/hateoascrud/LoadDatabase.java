package com.azad.hateoascrud;

import com.azad.hateoascrud.account.Account;
import com.azad.hateoascrud.account.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Configuration
public class LoadDatabase {

    private AccountRepository accountRepository;

    public LoadDatabase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            Account account1 = new Account("1982080185", 1021.99);
            Account account2 = new Account("1982032177", 231.50);
            Account account3 = new Account("1982094128", 6211.00);

            if (accountRepository.findAll().size() < 1) {
                accountRepository.save(account1);
                accountRepository.save(account2);
                accountRepository.save(account3);
            }

            System.out.println("Sample database initialized");
        };
    }
}
