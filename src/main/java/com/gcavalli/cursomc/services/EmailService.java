package com.gcavalli.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.gcavalli.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
