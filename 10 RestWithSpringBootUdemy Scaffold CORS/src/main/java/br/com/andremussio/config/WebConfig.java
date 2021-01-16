package br.com.andremussio.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.andremussio.serialization.converter.YamlJackson2HttpMessageConverter;

/*
 *Classe de configuração para permitir que a aplicação forneça JSON e XML 
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}

	public void addCorsMappings(CorsRegistry registry) {
		//registry.addMapping("/**"); //regex para qualquer origem (funciona apenas para verbos GET, PUT, POST e DELETE)
		
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		/*
		 * Content Negotiation por extensão
		 * --------------------------------
		 * Configuração para:
		 * 1. Desabilitar o uso do request parameter "format" para indicar o tipo de retorno;
		 * 2. Não ignorar o header parameter ACCEPT;
		 * 3. Configurar JSON como MediaType padrão;
		 * 4. Configurar JSON e XML com MediaTypes permitidos na aplicação.
		 * 
		 * Nesta forma, a chamada dos serviços deve incluir uma extensão ".json" ou ".xml" no final da URI para definir o formato desejado.
		 * Exemplo: .../api/person.xml
		 */
		/*
		configurer.favorParameter(false)
				.ignoreAcceptHeader(false)
				.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML);
		*/

		/*
		 * Content Negotiation por query param
		 * -----------------------------------
		 * Configuração para:
		 * 1. Habilitar o uso do query parameter "format" para indicar o tipo de retorno;
		 * 2. Definir o nome do query parameter para o tipo de retorno como sendo "mediaType";
		 * 3. Ignorar o header parameter ACCEPT;
		 * 3. Configurar JSON como MediaType padrão;
		 * 4. Configurar JSON e XML com MediaTypes permitidos na aplicação.
		 * 
		 * Nesta forma, a chamada dos serviços deve incluir um query parameter chamado "mediaType" para definir o tipo de retorno: "json" ou "xml".
		 * Exemplo: .../api/person?mediaType=xml
		 */
		/*
		configurer.favorPathExtension(false)
		.favorParameter(true)
		.parameterName("mediaType")
		.ignoreAcceptHeader(true)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML);
		*/
		
		/*
		 * Content Negotiation por header param
		 * ------------------------------------
		 * Configuração para:
		 * 1. Desabilitar o uso do query parameter "format" para indicar o tipo de retorno;
		 * 2. Não ignorar o header parameter ACCEPT;
		 * 3. Configurar JSON como MediaType padrão;
		 * 4. Configurar JSON e XML com MediaTypes permitidos na aplicação.
		 * 
		 * Nesta forma, a chamada dos serviços deve incluir um header parameter chamado "Accept" para definir o tipo de retorno: "application/json" ou "application/xml".
		 */
		configurer.favorPathExtension(false)
		.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MediaType.valueOf("application/x-yaml"));
	}
}
