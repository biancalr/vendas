package br.com.vendas.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.vendas.domain.entity.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Pedido implements Serializable{

	private static final long serialVersionUID = 4357595271251365480L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	@EqualsAndHashCode.Include
	@ToString.Include
    private Integer id;

    @ManyToOne
    @ToString.Include
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ToString.Include
    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @ToString.Include
    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;
    
    @Enumerated(EnumType.STRING)
    @Column
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

}
