package com.azad.moviepedia;

import com.azad.moviepedia.services.GenreService;
import com.azad.moviepedia.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviepediaApplication implements CommandLineRunner {

	private AuthService authService;
	private GenreService genreService;

	@Autowired
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	@Autowired
	public void setGenreService(GenreService genreService) { this.genreService = genreService; }

	public static void main(String[] args) {
		SpringApplication.run(MoviepediaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		authService.authInit();
		genreService.initGenres();
	}
}
