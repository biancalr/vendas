package br.com.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.domain.entity.ItemPedido;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer>{

}
