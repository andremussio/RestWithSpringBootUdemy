package br.com.andremussio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenApi() {
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
		return new OpenAPI()
				.info(new Info()
						.title("RESTfull API com Spring Boot 2.4.3")
						.version("v1")
						.description("API de estudos")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
	
}
