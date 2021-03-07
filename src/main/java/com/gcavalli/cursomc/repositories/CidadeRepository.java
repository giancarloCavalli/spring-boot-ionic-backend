package com.gcavalli.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcavalli.cursomc.domain.Cidade;
import com.gcavalli.cursomc.domain.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly = true)
	public List<Cidade> findAllByEstadoOrderByNome(Estado estado);
}
