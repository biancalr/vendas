package br.com.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {
		
}
