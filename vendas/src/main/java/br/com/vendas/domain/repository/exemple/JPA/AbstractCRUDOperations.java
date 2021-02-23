package br.com.vendas.domain.repository.exemple.JPA;

import java.util.List;

public abstract class AbstractCRUDOperations<B> {

	protected abstract B salvar(B entidade);

	protected abstract List<B> obterTodos();

	protected abstract B atualizar(B entidade);

	protected abstract void deletar(Integer id);

}
