package com.biblioteca.biblioteca;

import com.biblioteca.biblioteca.principal.Principal;
import com.biblioteca.biblioteca.principal.repository.AutorRepository;
import com.biblioteca.biblioteca.principal.repository.LibroRepository;
import com.biblioteca.biblioteca.principal.servicios.Conversor;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {
	@Autowired
	Principal principal;
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.inicio();
	}
}
