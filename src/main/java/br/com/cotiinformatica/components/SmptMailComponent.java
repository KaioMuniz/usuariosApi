package br.com.cotiinformatica.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmptMailComponent {

	@Autowired JavaMailSender javaMailSender;
	

	public void SendMail(String to, String subject, String text) throws Exception {
		
		var message = javaMailSender.createMimeMessage();
		var helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text, true);

		javaMailSender.send(message);
	}
	
}
