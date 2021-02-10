package com.gcavalli.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcavalli.cursomc.domain.Produto;
import com.gcavalli.cursomc.dto.ProdutoDTO;
import com.gcavalli.cursomc.resources.utils.URL;
import com.gcavalli.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	ProdutoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> search(
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "categorias", defaultValue = "") String categorias,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy) {
		String decodedNome = URL.decodeParam(nome);
		//TODO previnir situacao em que categorias = "" (quando usuário nao informa) - Isso se mais pra frente no curso não for abordado
		List<Integer> listIds = URL.decodeIntList(categorias);
		Page<Produto> pageProduto = service.search(decodedNome, listIds, page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> pageProdutoDto = pageProduto.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(pageProdutoDto);
	}
	
}
