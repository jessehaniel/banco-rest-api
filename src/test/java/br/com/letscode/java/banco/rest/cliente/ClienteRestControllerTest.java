package br.com.letscode.java.banco.rest.cliente;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ClienteRestControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(wac)
            .apply(springSecurity())
            .build();
    }

    @WithMockUser
    @Test
    void buscarCliente() throws Exception {
        mockMvc.perform(get("/clientes?nome=haniel"))
            .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void listarClientes() {
        mockMvc.perform(get("/clientes/page")
            .with(httpBasic("player", "player123")))
            .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void listarClientes_forbidden() {
        mockMvc.perform(get("/clientes/page")
            .with(httpBasic("admin", "admin123")))
            .andExpect(status().isForbidden());
    }

    @SneakyThrows
    @Test
    void listarClientes_unauthorized() {
        mockMvc.perform(get("/clientes/page"))
            .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    @Test
    void inserir() {
        mockMvc.perform(post("/clientes?pf=")
            .content(getClienteJson())
            .contentType(MediaType.APPLICATION_JSON)
            .with(httpBasic("admin", "admin123")))
            .andExpect(status().isCreated());
    }

    private String getClienteJson() throws JsonProcessingException {
        final var cliente = new ClientePF();
        cliente.setCpf("513.405.690-00");
        cliente.setNome("Jessé Haniel");
        cliente.setDataNascimento(LocalDate.of(1985, 9, 30));
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writeValueAsString(cliente);
    }

    @SneakyThrows
    @Test
    void inserir_forbidden() {
        mockMvc.perform(post("/clientes?pf=")
            .with(httpBasic("player", "player123")))
            .andExpect(status().isForbidden());
    }

}
