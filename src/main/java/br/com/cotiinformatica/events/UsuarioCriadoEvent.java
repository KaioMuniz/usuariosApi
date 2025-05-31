package br.com.cotiinformatica.events;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioCriadoEvent {
	
	private UUID id;
	private String nome;
	private String email;
	private Date dataHoraCriacao;
	

}
