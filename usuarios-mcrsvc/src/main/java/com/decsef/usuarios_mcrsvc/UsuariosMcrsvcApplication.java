package com.decsef.usuarios_mcrsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UsuariosMcrsvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosMcrsvcApplication.class, args);
	}

}
