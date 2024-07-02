package com.devsuperior.dsmeta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@SpringBootApplication
public class DsmetaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DsmetaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		System.out.println("Data atual do sistema: " + today);

		LocalDate result = today.minusYears(1L);

		System.out.println("Data com 1 ano antes: " + result);
	}
}
