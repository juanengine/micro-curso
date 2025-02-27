package com.decsef.mcsvc_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class McsvcCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsvcCursosApplication.class, args);
	}

}
