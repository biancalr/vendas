package br.com.vendas.domain.repository.exemple.JPA;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDAO<B> extends AbstractCRUDOperations<B>{
	
	@Autowired
	protected EntityManager entityManager;
	
	@Transactional
	protected abstract B salvar(B entidade);

	@Transactional(readOnly = true)
	protected abstract List<B> obterTodos();
	
	@Transactional
	protected abstract B index(Integer id);

	@Transactional
	protected abstract B atualizar(B entidade);

	@Transactional
	protected abstract void deletar(Integer id);

}
