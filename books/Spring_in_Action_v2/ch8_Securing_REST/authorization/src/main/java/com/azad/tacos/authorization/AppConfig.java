package com.azad.tacos.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public ApplicationRunner dataLoader(TacoUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(new TacoUser("habuma", encoder.encode("password")));
            repo.save(new TacoUser("tacochef", encoder.encode("password")));
        };
    }
}
