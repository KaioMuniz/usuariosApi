package br.com.cotiinformatica.components;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.events.UsuarioCriadoEvent;

@Component
public class RabbitMQPublisherComponent {

	@Autowired RabbitTemplate rabbitTemplate;
	@Autowired ObjectMapper objectMapper;
	@Autowired Queue queue;
	
	/*
	 * Método para enviar os dados do usuário criado
	 * para a fila do RabbitMQ
	 */
	public void publish(UsuarioCriadoEvent event) throws Exception {
		
		var json = objectMapper.writeValueAsString(event);
		rabbitTemplate.convertAndSend(queue.getName(), json);
	}
}
