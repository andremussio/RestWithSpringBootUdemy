package br.com.andremussio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.andremussio.config.FileStorageConfig;

/*
 * @SpringBootApplication: define a aplicação como uma aplicação SpringBoot.
 * @EnableConfigurationProperties: indica ao SpringBoot qual a classe que será utilizada para armazenar as configurações customizadas do application.properties. O Spring automaticamente lerá as propriedades e preencherá a classe.
 * @EnableAutoConfiguration: permite que o Application Context do Spring seja automaticamente carregado com base nos JARS e nas configurações definidas na aplicação.
 * @ComponentScan: diz para o SpringBoot que ele deve escanear os pacotes em busca dos arquivos de configuração da aplicação.
 */
@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageConfig.class
})
@EnableAutoConfiguration
@ComponentScan
public class Startup {
	
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
		
		// Gerando a senha inicial criptografada. Somente exemplo para caso de necessidade de gravar um usuário novo no banco!!
		/*
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		String result = bCryptPasswordEncoder.encode("admin123");
		System.out.println("My hash = " + result);
		*/
	}
	
}
