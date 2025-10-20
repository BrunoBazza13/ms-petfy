package br.com.petfy.ms_agendamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsAgendamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAgendamentoApplication.class, args);
	}

}
