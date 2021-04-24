package br.com.andremussio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.andremussio.security.jwt.JwtConfigurer;
import br.com.andremussio.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	/*
	 * Bean que define o algoritmo de criptografia das senhas dos usuários.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	/*
	 * Método que define um AuthenticationManager para a aplicação.
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/*
	 * Método que configura a aplicação.
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable() // Desabilita autenticação HTTP básica (SESSIONID)
			.csrf().disable() // Desabilita CSRF - Cross Site Request Forgery (Falsificação de requisições por outros sites). Estamos desabilitando este controle automático do Spring porque a seguir estamos definindo os Endpoints que podem ser requisitados.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Cria política de sessão STATELESS
			.and()
				.authorizeRequests() // Política de autorização de requisições
				.antMatchers("/auth/signin", "/api-docs/**", "swagger-ui.html**").permitAll() // Permite requisição desses endpoints sem autenticação do usuário!
				.antMatchers("/api/**").authenticated() // Permite requisição desses endpoints desde que o usuário esteja autenticado
				.antMatchers("/users").denyAll() // Bloqueia requisição desse endpoint
			.and()
			.cors() //Necessário para permitir a execução do React Preflight Request, que o React executa antes de cada request para verificar se o request tem permissão na API. Além dessa configuração, também é necessária a configuração CORS existente em WebConfig.java
			.and()
			.apply(new JwtConfigurer(tokenProvider)); // Aplica classe de configuração JWT
	}
}
