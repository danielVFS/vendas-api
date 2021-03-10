package com.daniel.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
<<<<<<< HEAD
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
=======

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

>>>>>>> 4e3ab1e11f423e598434a68e399c513da83d5674
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email");
		mailSender.send(msg);
		LOG.info("Email enviado");
<<<<<<< HEAD
		
=======
>>>>>>> 4e3ab1e11f423e598434a68e399c513da83d5674
	}

}
