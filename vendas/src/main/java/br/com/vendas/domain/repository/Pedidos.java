package br.com.vendas.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByCliente(Cliente cliente);
	
	List<Pedido> findByDataPedido(LocalDate dataPedido);
	
	@Query("select p from Pedido p left join fetch p.itens i where p.id = :id")
	Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
	
}
