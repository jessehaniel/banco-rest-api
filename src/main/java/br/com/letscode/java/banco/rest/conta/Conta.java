package br.com.letscode.java.banco.rest.conta;

import br.com.letscode.java.banco.rest.cliente.ClientePF;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Conta {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal saldo;

    //obrigatório: define em BD o relacionamento entre Conta e ClientePF
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClientePF clientePf;

}
