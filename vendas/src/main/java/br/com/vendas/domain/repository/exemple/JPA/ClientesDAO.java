package br.com.vendas.domain.repository.exemple.JPA;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.domain.entity.Cliente;

//@Repository
public class ClientesDAO extends AbstractDAO<Cliente> {

	@Override
	public Cliente salvar(Cliente cliente) {
		entityManager.persist(cliente);
		return cliente;
	}

	@Override
	public List<Cliente> obterTodos() {
		return entityManager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	}

	@Override
	public Cliente atualizar(Cliente cliente) {
		return entityManager.merge(cliente);
	}

	@Override
	public void deletar(Integer id) {
		Cliente cliente = entityManager.find(Cliente.class, id);
		if (!entityManager.contains(cliente)) {
			cliente = entityManager.merge(cliente);
		} 
		entityManager.remove(cliente);
	}

	@Override
	public Cliente index(Integer id) {
		return entityManager.find(Cliente.class, id);
	}

	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNome(String nome) {
		String jpql = " select c from Cliente c where c.nome like :nome";
		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}

}
