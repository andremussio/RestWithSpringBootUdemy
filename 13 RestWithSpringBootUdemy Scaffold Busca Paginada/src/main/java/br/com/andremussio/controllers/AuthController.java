package br.com.andremussio.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andremussio.repository.UserRepository;
import br.com.andremussio.security.AccountCredentialsVO;
import br.com.andremussio.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	UserRepository repository;
	
	@ApiOperation(value = "Authenticates a user by credentials and returns a token")
	@PostMapping(value = "/signin", produces = {"application/json", "application/xml", "application/x-yaml"},
			consumes = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		try {
			// Pega usuário e senha fornecidos no login
			var username = data.getUsername();
			var password = data.getPassword();
			
			// Executa método de autenticação para validar usuário e senha fornecidos.
			// Se a autenticação falhar, gera exceção BadCredentialsException.
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			// Se a autenticação passou, busca dados do usuário no banco de dados.
			var user = repository.findByUsername(username);
			var token = "";
			
			// Se encontrou usuário no banco de dados, cria token para ele.
			// Senão, gera exceção UsernameNotFoundException.
			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			
			// Cria objeto de retorno da autenticação para o cliente (Map).
			// Devolve o username e o token de autenticação gerado.
			Map<Object, Object> model = new HashMap<Object, Object>();
			model.put("username", username);
			model.put("token", token);
			
			// Devolve sinalização de OK junto com os dados de retorno da autenticação.
			return ok(model);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
