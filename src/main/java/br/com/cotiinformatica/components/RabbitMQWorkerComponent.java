package br.com.cotiinformatica.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.events.UsuarioCriadoEvent;

@Component
public class RabbitMQWorkerComponent {

	@Autowired SmtpMailComponent smtpMailComponent;
	@Autowired ObjectMapper objectMapper;
		
	/*
	 * Método para ler e processar a fila
	 */
	@RabbitListener(queues = "boas-vindas")
	public void consume(@Payload String message) throws Exception {
		
		//ler e deserializar o conteudo da mensagem gravada na fila
		var usuarioCriadoEvent = objectMapper.readValue(message, UsuarioCriadoEvent.class);
		
		//definir os parametros para envio de email
		var to = usuarioCriadoEvent.getEmail();
		var subject = "Parabéns, " + usuarioCriadoEvent.getNome() + "! Sua conta foi criada com sucesso.";
		var body = escreverEmailDeBoasVindas(usuarioCriadoEvent);
		
		//enviando o email
		smtpMailComponent.sendMail(to, subject, body);
	}
	
	private String escreverEmailDeBoasVindas(UsuarioCriadoEvent event) {
	    return """
	        <html>
	            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
	                <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
	                    <h2 style="color: #2d9cdb;">Olá, %s!</h2>
	                    <p>Seja bem-vindo ao nosso sistema. Sua conta foi criada com sucesso e agora você pode aproveitar todos os recursos disponíveis.</p>
	                    <p>Estamos muito felizes em tê-lo conosco. Se tiver alguma dúvida, não hesite em entrar em contato com nossa equipe de suporte.</p>
	                    <hr>
	                    <p style="font-size: 12px; color: #999999;">Este é um e-mail automático, por favor não responda.</p>
	                </div>
	            </body>
	        </html>
	        """.formatted(event.getNome());
	}
}






