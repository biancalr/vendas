package br.com.vendas.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

public abstract class AbstractController<T> {

	protected abstract T getEntidadeById(@PathVariable Integer id);
	
	protected abstract T save(T entidade);
	
	protected abstract void delete(@PathVariable Integer id);
	
	protected abstract List<T> find(T filtro);
	
}
