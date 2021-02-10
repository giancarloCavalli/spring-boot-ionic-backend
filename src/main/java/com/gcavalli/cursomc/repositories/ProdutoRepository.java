package com.gcavalli.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcavalli.cursomc.domain.Categoria;
import com.gcavalli.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
	/*
	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE lower(obj.nome) LIKE lower(concat('%', :nome, '%')) AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
			@Param("nome") String nome, 
			@Param("categorias") List<Categoria> categorias, 
			Pageable pageRequest);
	*/
	
}
