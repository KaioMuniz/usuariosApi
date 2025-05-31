package br.com.cotiinformatica.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutenticarUsuarioResponse {

	private String mensagem;
	
	private UUID id;
	private String nome;
	private String email;
	
	private String accessToken;
	private Date dataHoraAcesso;
	private Date dataHoraExpiracao;
}
