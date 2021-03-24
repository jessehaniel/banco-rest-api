package br.com.letscode.java.banco.rest.cliente;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Cliente {

    @NotBlank(message = "Nome é um campo obrigatório")
    private String nome;
    public abstract String getDocumento();

}
