package br.com.cotiinformatica.configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfiguration {

	 @Bean
	 public OpenAPI customOpenAPI() {
		 return new OpenAPI()
				 .info(new Info()
						 .title("API Usuários - Coti Informatica")
	                     .version("1.0")
	                     .description("Documentação da API desenvolvida na Turma Java WebDeveloper")
	                     .contact(new Contact()
	                    		 .name("Suporte Coti Informatica")
	                             .url("https://www.cotiinformatica.com.br/contato")
	                             .email("suporte@cotiinformatica.com.br"))
	                     .license(new License()
	                    		 .name("Licença MIT")
	                             .url("https://opensource.org/licenses/MIT")))
	                	 .servers(List.of(
	                        new Server().url("http://localhost:8081").description("Ambiente de Desenvolvimento"),
	                        new Server().url("https://api.cotiinformatica.com.br").description("Ambiente de Produção")
	                ));
	    }
}
