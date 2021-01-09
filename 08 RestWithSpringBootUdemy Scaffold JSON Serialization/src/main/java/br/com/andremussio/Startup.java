package br.com.andremussio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * @SpringBootApplication: define a aplicação como uma aplicação SpringBoot.
 * @EnableAutoConfiguration: permite que o Application Context do Spring seja automaticamente carregado com base nos JARS e nas configurações definidas na aplicação.
 * @ComponentScan: diz para o SpringBoot que ele deve escanear os pacotes em busca dos arquivos de configuração da aplicação.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class Startup {
	
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}
	
}
