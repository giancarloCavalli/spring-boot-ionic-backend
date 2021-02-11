package com.gcavalli.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcavalli.cursomc.domain.Pagamento;
import com.gcavalli.cursomc.repositories.PagamentoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	PagamentoRepository repo;
	
	public Pagamento insert(Pagamento obj) {
		return repo.save(obj);
	}
	
}
