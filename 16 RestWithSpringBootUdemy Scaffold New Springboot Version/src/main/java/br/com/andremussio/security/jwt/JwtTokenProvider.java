package br.com.andremussio.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.andremussio.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}") //Indica o valor default do atributo. Mas permite alteração do valor através da referida chave a ser colocada no application.properties
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length:3600000}") //Indica o valor default do atributo. Mas permite alteração do valor através da referida chave a ser colocada no application.properties
	private long validityInMilliseconds = 3600000; //1h de validade para o token
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostConstruct
	public void init() {
		// Criptografa o segredo previamente definido em Base64
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	/*
	 * Método responsável por criar um novo token para um determinado usuário após sua autenticação validada.
	 */
	public String createToken( String username, List<String> roles) {
		// Definição da JWTS Claims ("certificação" do token)
		Claims claims = Jwts.claims().setSubject(username);
		// Acrescentando a lista de roles do usuário em claims
		claims.put("roles", roles);
		
		Date now = new Date();
		Date valid = new Date(now.getTime() + validityInMilliseconds); // Definindo a data/hora de expiração do token gerado
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(valid)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	/*
	 * Método que retorna a autenticação do usuário. Usado apenas após a validação do login!
	 */
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserNameFromToken(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/*
	 * Método que obtém o username de dentro do token.
	 */
	private String getUserNameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	/*
	 * Método que pega o token no header do request e devolve o token puro.
	 * Por padrão, o token é concatenado com a palavra "Bearer " em cada request.
	 */
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization"); // Pega o token contido no parâmetro Authorization no Header do request.
		
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}
	
	/*
	 * Método que verifica se o token é válido e não está expirado.
	 */
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
								.setSigningKey(secretKey)
								.parseClaimsJws(token);
			
			// Se a data/hora de expiração do token for anterior à data/hora corrente, token inválido!
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid token");
		}
	}
}
