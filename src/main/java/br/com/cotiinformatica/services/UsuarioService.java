package br.com.cotiinformatica.services;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.JwtBearerComponent;
import br.com.cotiinformatica.components.RabbitMQPublisherComponent;
import br.com.cotiinformatica.components.SHA256Component;
import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.events.UsuarioCriadoEvent;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired UsuarioRepository usuarioRepository;
	@Autowired ModelMapper modelMapper;
	@Autowired SHA256Component sha256Component;
	@Autowired JwtBearerComponent jwtBearerComponent;
	@Autowired RabbitMQPublisherComponent rabbitMQPublisherComponent;
	
	public AutenticarUsuarioResponse autenticarUsuario(AutenticarUsuarioRequest request) {
		
		var usuario = usuarioRepository.find(
				request.getEmail(), 
				sha256Component.encrypt(request.getSenha())
				);
		
		if(usuario == null)
			throw new IllegalArgumentException("Usuário não encontrado. Verifique o email e senha informados.");
		
		var response = modelMapper.map(usuario, AutenticarUsuarioResponse.class);
		response.setMensagem("Usuário autenticado com sucesso!");
		response.setDataHoraAcesso(new Date());
		response.setAccessToken(jwtBearerComponent.createAccessToken(usuario.getEmail()));
		response.setDataHoraExpiracao(jwtBearerComponent.getExpiration());
		
		return response;
	}
	
	public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) {
		
		if(usuarioRepository.existsByEmail(request.getEmail()))
				throw new IllegalArgumentException("E-mail já cadastrado! Tente outro.");
		
		var usuario = modelMapper.map(request, Usuario.class);
		usuario.setId(UUID.randomUUID());
		usuario.setSenha(sha256Component.encrypt(request.getSenha()));
		
		usuarioRepository.save(usuario);
		
		var usuarioCriadoEvent = modelMapper.map(usuario, UsuarioCriadoEvent.class);
		usuarioCriadoEvent.setDataHoraCriacao(new Date());
		
		try {
			rabbitMQPublisherComponent.publish(usuarioCriadoEvent);
		}
		catch(Exception e) {
			//TODO gravar log de erro
			e.printStackTrace();
		}
		
		
		var response = modelMapper.map(usuario, CriarUsuarioResponse.class);
		response.setMensagem("Usuário cadastrado com sucesso!");
		
		return response;
	}
}
