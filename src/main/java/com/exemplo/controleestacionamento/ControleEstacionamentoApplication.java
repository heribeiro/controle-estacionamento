package com.exemplo.controleestacionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ControleEstacionamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleEstacionamentoApplication.class, args);
	}

	@GetMapping("/teste")
	public String index(){
		return "Ola mundo!Ola mundo!Ola mundo!Ola mundo!Ola mundo!Ola mundo!Ola mundo!Ola mundo!";
	}

}
