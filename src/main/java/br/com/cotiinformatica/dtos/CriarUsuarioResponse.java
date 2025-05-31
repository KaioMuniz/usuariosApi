package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CriarUsuarioResponse {

	private String mensagem;
	
	private UUID id;
	private String nome;
	private String email;
}
