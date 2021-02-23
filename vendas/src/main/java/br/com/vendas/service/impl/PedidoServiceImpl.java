package br.com.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.ItemPedido;
import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.domain.entity.Produto;
import br.com.vendas.domain.entity.enums.StatusPedido;
import br.com.vendas.domain.repository.Clientes;
import br.com.vendas.domain.repository.ItensPedido;
import br.com.vendas.domain.repository.Pedidos;
import br.com.vendas.domain.repository.Produtos;
import br.com.vendas.exception.PedidoNaoEncontradoException;
import br.com.vendas.exception.RegraDeNegocioException;
import br.com.vendas.rest.dto.ItemPedidoDTO;
import br.com.vendas.rest.dto.PedidoDTO;
import br.com.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final Pedidos repository;
	private final Clientes clientesRepository;
	private final Produtos produtosRepository;
	private final ItensPedido itensPedidoRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente;
		Cliente cliente;
		
		idCliente = dto.getCliente();
		cliente = clientesRepository
							.findById(idCliente)
							.orElseThrow( () ->
								new RegraDeNegocioException(
										"Código do Cliente inválido: "
												+ idCliente));
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
		pedido.setItens(itensPedido);
		
		repository.save(pedido);
		itensPedidoRepository.saveAll(itensPedido);
		
		return pedido;
	}
	
	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if (itens.isEmpty()) {
			throw new RegraDeNegocioException(
					"Náo é possível realizar um pedido sem itens");
		}
		
		return itens.stream().map( dto -> {
			Integer idProduto = dto.getProduto();
			Produto produto = produtosRepository
								.findById(idProduto)
								.orElseThrow( () ->
									new RegraDeNegocioException(
											"Código do produto inválido: "
													+ idProduto));
			
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			return itemPedido;
		}).collect(Collectors.toList());
		
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido novoStatus) {
		repository
				.findById(id)
				.map(pedido -> {
					pedido.setStatus(novoStatus);
					return repository.save(pedido);
				})
				.orElseThrow(() -> 
					new PedidoNaoEncontradoException("O Pedido de id " + id + " não foi encontrado"));
		
	}
	
	
	
	
	
	
	
}
