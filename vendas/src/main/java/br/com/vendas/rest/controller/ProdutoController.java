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

import br.com.vendas.domain.entity.Produto;
import br.com.vendas.domain.repository.Produtos;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController extends AbstractController<Produto> {
	
	private Produtos repository;

	public ProdutoController(Produtos produtos) {
		this.repository = produtos;
	}
	
	@GetMapping("/{id}")
	public Produto getEntidadeById(@PathVariable Integer id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Produto de id " + id + " não encontrado") );

	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto save(@RequestBody @Validated Produto produto) {
		return repository.save(produto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository
			.findById(id)
			.map( produto -> {
				repository.delete(produto);
				return Void.TYPE; 
				})
			.orElseThrow( () -> new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"Produto de id " + id + " não encontrado") );

	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody @Validated Produto produto) {
		repository
			.findById(produto.getId())
			.map( produtoExistente -> { 
					produto.setId(produtoExistente.getId());
					repository.save(produto);
					return ResponseEntity.noContent().build();
				})
			.orElseThrow( () -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Produto " + produto.toString() + " não encontrado"));

	}
	
	@GetMapping
	public List<Produto> find(Produto filtro) {
		ExampleMatcher matcher = ExampleMatcher
								.matching()
								.withIgnoreCase()
								.withStringMatcher(
										StringMatcher.CONTAINING);
		
		Example<Produto> example = Example.of(filtro, matcher);
		return repository.findAll(example);

	}

}
