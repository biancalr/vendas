package br.com.vendas.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vendas.domain.entity.ItemPedido;
import br.com.vendas.domain.entity.Pedido;
import br.com.vendas.domain.entity.enums.StatusPedido;
import br.com.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import br.com.vendas.rest.dto.InformacaoItemPedidoDTO;
import br.com.vendas.rest.dto.InformacaoPedidoDTO;
import br.com.vendas.rest.dto.PedidoDTO;
import br.com.vendas.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody @Validated PedidoDTO pedidoDTO) {
		Pedido pedido = service.salvar(pedidoDTO);
		return pedido.getId();
	}
	
	@GetMapping("/{id}")
	public InformacaoPedidoDTO getEntidadeById(@PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map( p -> converter(p) )
				.orElseThrow( () -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Pedido de id " + id + " não encontrado") );
	}
	
	private InformacaoPedidoDTO converter(Pedido pedido) {
		return InformacaoPedidoDTO
					.builder()
					.codigo(pedido.getId())
					.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
					.cpf(pedido.getCliente().getCpf())
					.nomeCliente(pedido.getCliente().getNome())
					.total(pedido.getTotal())
					.status(pedido.getStatus().name())
					.itens(converter(pedido.getItens()))
					.build();
	}

	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(
				
				item -> InformacaoItemPedidoDTO
						.builder()
						.descricao(item.getProduto().getDescricao())
						.preco_unitario(item.getProduto().getPreco_unitario())
						.quantidade(item.getQuantidade())
						.build()
				
				).collect(Collectors.toList());
		
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id,
					@RequestBody AtualizacaoStatusPedidoDTO dto) {

		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
		
	}

}
