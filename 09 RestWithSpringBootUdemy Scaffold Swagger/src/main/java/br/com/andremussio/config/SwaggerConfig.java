package br.com.andremussio.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		// A documentação JSON gerada pelo Swagger estará disponível no endpoint '/v2/api-docs'
		// Para acessar a documentação Swagger formatada em HTML, acessar '/swagger-ui.html'
		/*
		 * Configuração básica para documentar tudo
		 */
		/*
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())  //Indica que serão mapeados para documentação todos os controllers da API
				.paths(PathSelectors.any()) //Indica que serão mapeados para documentação todos os paths da API
				.build();
		*/
		
		/*
		 * Configuração para documentar pacotes específicos
		 */
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.andremussio"))  //Indica que serão mapeados para documentação todos os controllers do pacote base informado
				.paths(PathSelectors.any()) //Indica que serão mapeados para documentação todos os paths da API
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("RESTfull API With Spring Boot 2.1.3", "API de estudos", "v1", "Termos de serviço URL", new Contact("André Mussio", "github.com/repo", "almussio@email.com"), "Licença da API", "Licença URL", Collections.emptyList());
	}
	
}
