package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuários", description = "Operações relacionadas a usuários.")
@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

	/*
	 * Injeção de dependência do serviço de usuários.
	 */
	@Autowired UsuarioService usuarioService;
	
	@Operation(summary = "Autenticação de usuários", description = "Serviço para identificar e autenticar usuários.")
	@PostMapping("autenticar")
	public AutenticarUsuarioResponse autenticar(@RequestBody @Valid AutenticarUsuarioRequest request) {
		return usuarioService.autenticarUsuario(request);
	}

	@Operation(summary = "Criação de usuários", description = "Serviço para cadastro de novos usuários.")
	@PostMapping("criar")
	public CriarUsuarioResponse criar(@RequestBody @Valid CriarUsuarioRequest request) {
		return usuarioService.criarUsuario(request);
	}
}
