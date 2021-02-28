package com.gcavalli.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcavalli.cursomc.domain.Cliente;
import com.gcavalli.cursomc.repositories.ClienteRepository;
import com.gcavalli.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	EmailService emailService;

	@Autowired
	ClienteService clienteService;
	
	//usado repository ao inves de service para nao haver necessidade do usuario estar logado
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private Random random = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteService.findByEmail(email);
		if (cliente == null)
			throw new ObjectNotFoundException("Email " + email + " não encontrado");

		String newPassword = newPassword();
		cliente.setSenha(passwordEncoder.encode(newPassword));
		clienteRepository.save(cliente);

		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int x = random.nextInt(3);
		
		if (x == 0) //gera numero
			return (char) (random.nextInt(10) + 48);
		else if (x == 1) //gera letra maiuscula
			return (char) (random.nextInt(26) + 65);
		else //gera letra minuscula
			return (char) (random.nextInt(26) + 97);
	}

}
