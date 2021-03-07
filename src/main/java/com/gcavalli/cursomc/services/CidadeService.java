package com.gcavalli.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcavalli.cursomc.domain.Cidade;
import com.gcavalli.cursomc.domain.Estado;
import com.gcavalli.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findAllByEstado(Estado estado) {
		return repo.findAllByEstadoOrderByNome(estado);
	}
	
	
}
