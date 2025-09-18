package br.com.petfy.ms_agendamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsAgendamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAgendamentoApplication.class, args);
	}

}
