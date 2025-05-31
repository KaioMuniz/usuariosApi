package br.com.cotiinformatica.components;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtBearerComponent {

	/*
	 * Atributo para capturar a chave de assinatura dos tokens
	 */
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	/*
	 * Atributo para capturar o tempo de expiração dos tokens
	 */
	@Value("${jwt.expiration}")
	private String jwtExpiration;
	
	/*
	 * Método para calcular e retornar a 
	 * data e hora de expiração do token 
	 */
	public Date getExpiration() {
		var dataAtual = new Date();
		return new Date(dataAtual.getTime() + Integer.parseInt(jwtExpiration));
	}
	
	/*
	 * Método para gerar e retornar o Token JWT
	 */
	public String createAccessToken(String userName) {
		return Jwts.builder()
				.setSubject(userName) //nome do usuário autenticado
				.setIssuedAt(new Date()) //data e hora de geração do token
				.setExpiration(getExpiration()) //data e hora de expiração do token
				.signWith(SignatureAlgorithm.HS256, jwtSecret) //assinatura do token
				.compact();
	}
}









