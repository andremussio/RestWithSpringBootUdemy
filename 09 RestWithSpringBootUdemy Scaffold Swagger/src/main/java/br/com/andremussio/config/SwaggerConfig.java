package br.com.andremussio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
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
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())  //Indica que serão mapeados para documentação todos os controllers da API
				.paths(PathSelectors.any()) //Indica que serão mapeados para documentação todos os paths da API
				.build();
	}
	
}
