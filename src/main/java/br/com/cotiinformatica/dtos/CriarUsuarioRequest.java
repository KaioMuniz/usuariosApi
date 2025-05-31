package br.com.cotiinformatica.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CriarUsuarioRequest {

	@Size(min = 8, max = 100, message = "O nome do usuário deve ter entre 8 e 100 caracteres.")
	@NotEmpty(message = "Por favor, informe o nome do usuário.")
	private String nome;

	@Email(message = "Por favor, informe um e-mail válido.")
	@NotEmpty(message = "Por favor, informe o e-mail do usuário.")
	private String email;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,}$", 
			message = "A senha deve conter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e símbolos.")
	@NotEmpty(message = "Por favor, informe a senha do usuário.")
	private String senha;
}
