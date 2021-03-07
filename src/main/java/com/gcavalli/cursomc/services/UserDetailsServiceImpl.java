package com.gcavalli.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcavalli.cursomc.domain.Cliente;
import com.gcavalli.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	ClienteService clienteService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteService.findByEmailNoAuthRequired(email);
		
		if (cli == null)
			throw new UsernameNotFoundException("Email \"" + email + "\" inválido");
		
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis()); 
	}

}
