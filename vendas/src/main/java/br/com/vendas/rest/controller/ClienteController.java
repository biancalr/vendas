package br.com.vendas.rest.controller;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.repository.Clientes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController extends AbstractController<Cliente> {

	private Clientes repository;

	public ClienteController(Clientes clientes) {
		this.repository = clientes;
	}

	@ApiOperation("Obter detalhes de um Cliente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente encontrado"),
		@ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
	})
	@GetMapping("/{id}")
	public Cliente getEntidadeById(@PathVariable @ApiParam("ID do Cliente") Integer id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Cliente com id " + id + " não encontrado"));

	}

	@ApiOperation("Salva um novo Cliente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente salvo"),
		@ApiResponse(code = 404, message = "Erro de validação")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody @Validated Cliente cliente) {
		return repository.save(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository.findById(id)
				.map( cliente -> {
					repository.delete(cliente);
					return Void.TYPE;
				})
				.orElseThrow( () -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Cliente com id " + id + " não encontrado"));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, 
					   @RequestBody @Validated Cliente cliente) {
		repository
			.findById(id)
			.map(clienteExistente -> {
					cliente.setId(clienteExistente.getId());
					repository.save(cliente);
					return ResponseEntity.noContent().build();
			})
			.orElseThrow( () -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Cliente com id " + id + " não encontrado"));
	}

	@GetMapping
	public List<Cliente> find(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher
								.matching()
								.withIgnoreCase()
								.withStringMatcher(
										StringMatcher.CONTAINING);
		Example<Cliente> example = Example.of(filtro, matcher);
		return repository.findAll(example);
		
	}

}
