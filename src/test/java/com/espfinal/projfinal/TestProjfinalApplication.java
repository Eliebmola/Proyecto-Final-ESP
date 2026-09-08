package com.espfinal.projfinal;

import org.springframework.boot.SpringApplication;

public class TestProjfinalApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProjfinalApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
