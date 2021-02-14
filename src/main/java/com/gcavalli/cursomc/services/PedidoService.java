package com.gcavalli.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gcavalli.cursomc.domain.ItemPedido;
import com.gcavalli.cursomc.domain.PagamentoComBoleto;
import com.gcavalli.cursomc.domain.Pedido;
import com.gcavalli.cursomc.domain.Produto;
import com.gcavalli.cursomc.domain.enums.EstadoPagamento;
import com.gcavalli.cursomc.repositories.ItemPedidoRepository;
import com.gcavalli.cursomc.repositories.PedidoRepository;
import com.gcavalli.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoService.insert(obj.getPagamento());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getCliente().getPedidos().add(obj);
		Produto produto;
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.00);
			produto = produtoService.find(ip.getProduto().getId());
			ip.setPreco(produto.getPreco());
			ip.setPedido(obj);
			ip.setProduto(produto);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
}
