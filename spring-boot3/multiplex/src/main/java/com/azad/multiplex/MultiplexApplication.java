package com.azad.multiplex;

import com.azad.multiplex.common.RoleCreator;
import com.azad.multiplex.model.constant.RoleType;
import com.azad.multiplex.model.entity.MemberEntity;
import com.azad.multiplex.model.entity.RoleEntity;
import com.azad.multiplex.repository.MemberRepository;
import com.azad.multiplex.repository.RoleRepository;
import com.azad.multiplex.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class MultiplexApplication implements CommandLineRunner {

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(MultiplexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		authService.authInit();
	}
}
