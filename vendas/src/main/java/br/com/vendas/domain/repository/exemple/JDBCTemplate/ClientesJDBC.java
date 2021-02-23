package br.com.vendas.domain.repository.exemple.JDBCTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.vendas.domain.entity.Cliente;

//@Repository
public class ClientesJDBC {

	private static String INSERT = "insert into cliente (nome) values (?)";
	private static String SELECT_ALL = "select * from cliente";
	private static String UPDATE = "update cliente set nome = ? where id = ?";
	private static String DELETE = "delete from cliente where id = ?";

//	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Cliente salvar(Cliente cliente) {
		jdbcTemplate.update(INSERT, new Object[] { cliente.getNome() });
		return cliente;
	}

	@SuppressWarnings("deprecation")
	public List<Cliente> buscarPorNome(String nome) {
		return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ? "), new Object[] { "%" + nome + "%" },
				obterClienteRowMapper());
	}

	public List<Cliente> obterTodos() {
		return jdbcTemplate.query(SELECT_ALL, obterClienteRowMapper());
	}

	public Cliente atualizar(Cliente cliente) {
		jdbcTemplate.update(UPDATE, new Object[] { cliente.getNome(), cliente.getId() });
		return cliente;
	}

	public void deletar(Integer id) {
		jdbcTemplate.update(DELETE, new Object[] { id });
	}

	private RowMapper<Cliente> obterClienteRowMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer id = rs.getInt("id");
				String nome = rs.getString("nome");
				return new Cliente(id, nome);
			}
		};
	}

}
