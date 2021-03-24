package br.com.letscode.java.banco.rest.cliente;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientePJ extends Cliente {

    public static final double TAXA_EXTRA = 0.005;
    public static final double RENDIMENTO_EXTRA = 0.02;

    private String razaoSocial;
    private Long cnpj;
    private LocalDate dataAbertura;

    @Override
    public String getDocumento() {
        return this.cnpj.toString().replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    @Override
    public String getNome() {
        return this.getRazaoSocial();
    }

    @Override
    public void setNome(String nome) {
        this.setRazaoSocial(nome);
    }

    public void setCnpj(String cnpj) {
        this.setCnpj(Long.valueOf(cnpj.replaceAll("\\D", "")));
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

}
