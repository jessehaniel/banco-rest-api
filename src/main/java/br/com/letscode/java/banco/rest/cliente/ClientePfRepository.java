package br.com.letscode.java.banco.rest.cliente;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientePfRepository extends JpaRepository<ClientePF, Long> {

    //select * from CLIENTE_PF where nome = :nome
    List<ClientePF> findByNomeContaining(String nome);

    //select * from CLIENTE_PF where data_nascimento between :dataInicio AND :dataFim
    List<ClientePF> findByDataNascimentoBetween(LocalDate dataInicio, LocalDate dataFim);

}
