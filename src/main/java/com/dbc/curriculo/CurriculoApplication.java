package com.dbc.curriculo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurriculoApplication{

	public static void main(String[] args) {
		SpringApplication.run(CurriculoApplication.class, args);
	}

}
