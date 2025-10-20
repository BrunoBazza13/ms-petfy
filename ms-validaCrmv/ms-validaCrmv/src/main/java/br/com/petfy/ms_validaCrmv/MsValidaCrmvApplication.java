package br.com.petfy.ms_validaCrmv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsValidaCrmvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsValidaCrmvApplication.class, args);
	}

}
