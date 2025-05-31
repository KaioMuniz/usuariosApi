package br.com.cotiinformatica.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	/*
	 * Capturando o valor do chave do application.properties
	 * onde foi configurado o nome da fila do RabbitMQ
	 */
	@Value("${rabbitmq.queue_name}")
	private String queueName;
	
	/*
	 * Método de configuração para que o projeto 
	 * possa conectar-se na fila do RabbitMQ
	 */
	@Bean
	public Queue queue() {
		return new Queue(queueName, true);
	}
}
