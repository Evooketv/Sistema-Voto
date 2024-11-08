package com.pml.sistema_voto.controller;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.Pessoa;
import com.pml.sistema_voto.entity.Voto;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.repositories.PautaRepository;
import com.pml.sistema_voto.service.VotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VotoControllerTest {

    @MockBean
    private VotoService votoService;

    @MockBean
    private PautaRepository pautaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registrarVoto() throws Exception {

        Pauta pauta = new Pauta();
        pauta.setId(1L);


        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);


        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setPessoa(pessoa);
        voto.setOpcao(OpcaoVoto.SIM);


        when(votoService.registrarVoto(any(Voto.class))).thenReturn(voto);


        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));


        mockMvc.perform(post("/votos")
                        .contentType("application/json")
                        .content("{\"pautaId\": 1, \"opcaoVoto\": \"SIM\", \"descricao\": \"Voto de teste\"}"))
                .andExpect(status().isOk());
    }


    @Test
    void registrarVotoBadRequest() throws Exception {
        Pauta pauta = new Pauta();
        pauta.setId(1L);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        when(votoService.registrarVoto(any(Voto.class))).thenThrow(new IllegalArgumentException("Pauta não encontrada"));

        mockMvc.perform(post("/votos")
                        .contentType("application/json")
                        .content("{\"pautaId\": 1, \"opcaoVoto\": \"SIM\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Pauta não encontrada"));
    }

}
