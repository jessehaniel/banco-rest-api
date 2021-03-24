package br.com.letscode.java.banco.rest.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NomeVazioException extends RuntimeException {

    public NomeVazioException() {
        super("Nome é um campo obrigatório");
    }
}
