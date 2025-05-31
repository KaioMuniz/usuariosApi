package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjetoApiUsuariosApplicationTests {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper mapper;
	
	private static final Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));
	
	private static String email;
	private static String senha;
	
	@Test
	@Order(1)
	void deveCriarUmUsuarioComSucesso() throws Exception {
		
		var request = new CriarUsuarioRequest(); //criando um objeto DTO		 
		request.setNome(faker.name().fullName()); //gerando um nome de usuário
		request.setEmail(faker.internet().emailAddress()); //gerando um email de usuário
		request.setSenha("@Teste2025"); //criando a senha do usuário
				
		mockMvc.perform(
			post("/api/usuarios/criar") //fazendo uma chamada POST para a API
				.contentType(MediaType.APPLICATION_JSON) //enviando no formato JSON
				.content(mapper.writeValueAsString(request)) //serializando e enviando os dados
		)
		.andExpect(status().isOk()) //verificando se o status retornado foi OK (HTTP 200)
		.andDo(result -> { //capturando a resposta da API
			
			//deserializando os dados obtidos da API
			var json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			var response = mapper.readValue(json, CriarUsuarioResponse.class);
			
			assertNotNull(response); //a resposta não pode ser null
			assertNotNull(response.getId()); //o id retornado não pode ser null
			
			assertTrue(response.getMensagem().contains("Usuário cadastrado com sucesso!"));
			
			assertEquals(response.getNome(), request.getNome());
			assertEquals(response.getEmail(), request.getEmail());
		});
		
		//armazenando o email e senha gerados neste teste
		email = request.getEmail();
		senha = request.getSenha();
	}
	
	@Test
	@Order(2)
	void deveRetornarErroQuandoSenhaForFraca() throws Exception {
		
		var request = new CriarUsuarioRequest();  
		request.setNome(faker.name().fullName()); 
		request.setEmail(faker.internet().emailAddress());
		request.setSenha("testecoti"); 
				
		mockMvc.perform(
			post("/api/usuarios/criar") 
				.contentType(MediaType.APPLICATION_JSON) 
				.content(mapper.writeValueAsString(request)) 
		)
		.andExpect(status().isBadRequest())
		.andDo(result -> { 			
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);			
			assertTrue(content.contains("A senha deve conter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e símbolos."));
		});
	}
	
	@Test
	@Order(3)
	void deveRetornarErroQuandoEmailJaExiste() throws Exception {
		
		var request = new CriarUsuarioRequest();  
		request.setNome(faker.name().fullName()); 
		request.setEmail(email); //usando o email do usuário já cadastrado
		request.setSenha(senha);  //usando a senha do usuário já cadastrado
				
		mockMvc.perform(
			post("/api/usuarios/criar") 
				.contentType(MediaType.APPLICATION_JSON) 
				.content(mapper.writeValueAsString(request)) 
		)
		.andExpect(status().isBadRequest())
		.andDo(result -> { 			
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);			
			assertTrue(content.contains("E-mail já cadastrado! Tente outro."));
		});
	}
	
	@Test
	@Order(4)
	void deveAutenticarUsuarioComSucesso() throws Exception {
		
		var request = new AutenticarUsuarioRequest();		 
		request.setEmail(email); //email do usuário cadastrado no primeiro teste
		request.setSenha(senha);  //senha do usuário cadastrado no primeiro teste
				
		mockMvc.perform(
			post("/api/usuarios/autenticar") 
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)) 
		)
		.andExpect(status().isOk()) 
		.andDo(result -> { 
			
			var json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			var response = mapper.readValue(json, AutenticarUsuarioResponse.class);
			
			assertNotNull(response);
			assertNotNull(response.getId());
			assertNotNull(response.getAccessToken());
			
			assertTrue(response.getMensagem().contains("Usuário autenticado com sucesso!"));
		});		
	}
	
	@Test
	@Order(5)
	void deveNegarAcessoQuandoCredenciaisInvalidas() throws Exception {
		
		var request = new AutenticarUsuarioRequest();		 
		request.setEmail(faker.internet().emailAddress()); //email não existente
		request.setSenha("@Teste12345"); //senha não existente
				
		mockMvc.perform(
			post("/api/usuarios/autenticar") 
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)) 
		)
		.andExpect(status().isBadRequest()) 
		.andDo(result -> { 
			
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			assertTrue(content.contains("Usuário não encontrado. Verifique o email e senha informados."));
		});	
		
	}
}
