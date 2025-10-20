package br.com.petfy.ms_procimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsProcimentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProcimentoApplication.class, args);
	}

}
