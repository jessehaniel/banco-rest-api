package br.com.letscode.java.banco.rest.cliente;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/clientes")
@RestController
public class ClienteRestController {

    private final ClientePfRepository repository;

    public ClienteRestController(ClientePfRepository repository) {
        this.repository = repository;
    }

    @GetMapping("page")
    @PreAuthorize("hasRole('PLAYER')")
    public Page<ClientePF> listarClientes(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    //http://localhost:8080/clientes?nome=jes
    @PreAuthorize("isAuthenticated()")
    @GetMapping(params = "nome")
    public List<ClientePF> buscarCliente(@RequestParam String nome) {//jes
        return this.repository.findByNomeContaining(nome);
    }

    //http://localhost:8080/clientes?pf=
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(params = "pf")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente inserir(@Valid @RequestBody ClientePF cliente) {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new NomeVazioException();
        }
        this.repository.save(cliente);
        log.info("Novo Cliente PF cadastrado [{}]", cliente.toString());
        return cliente;
    }

    //http://localhost:8080/clientes?pj=
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(params = "pj")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente inserir(@RequestBody ClientePJ cliente) {
        log.info("Novo Cliente PJ cadastrado [{}]", cliente.toString());
        return cliente;
    }


}
