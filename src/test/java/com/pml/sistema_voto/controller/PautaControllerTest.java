package com.pml.sistema_voto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pml.sistema_voto.entity.PautaDTO;
import com.pml.sistema_voto.entity.PautaResponseDTO;
import com.pml.sistema_voto.entity.VotoResponseDTO;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.service.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PautaControllerTest {

    @MockBean
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void criarPauta() throws Exception {

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setDescricao("Tipo de Pauta");
        pautaDTO.setInicioPauta(LocalDateTime.now().plusDays(1));
        pautaDTO.setFimPauta(LocalDateTime.now().plusDays(2));

        PautaResponseDTO responseDTO = new PautaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setDescricao(pautaDTO.getDescricao());
        responseDTO.setInicioPauta(pautaDTO.getInicioPauta());
        responseDTO.setFimPauta(pautaDTO.getFimPauta());


        when(pautaService.createPauta(any(PautaDTO.class))).thenReturn(responseDTO);


        mockMvc.perform(post("/pauta/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaDTO))) // Usando o ObjectMapper injetado
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Tipo de Pauta"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void iniciarVotacao() throws Exception {
        VotoResponseDTO votoRequestDTO = new VotoResponseDTO(1L, 1L, OpcaoVoto.SIM, true, "Descrição do Voto", null, null, null);
        VotoResponseDTO expectedResponse = new VotoResponseDTO(1L, 1L, OpcaoVoto.SIM, true, "Voto registrado com sucesso", null, null, "Voto registrado com sucesso");


        when(pautaService.iniciarVotacao(1L, OpcaoVoto.SIM, "12345678901")).thenReturn(expectedResponse);

        mockMvc.perform(post("/pauta/iniciarVotacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pautaId\":1, \"opcaoVoto\":\"SIM\", \"cpf\":\"12345678901\", \"descricao\":\"Descrição do Voto\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagemSucesso").value("Voto registrado com sucesso"));
    }

    @Test
    void iniciarVotacaoBadRequest() throws Exception {

        VotoResponseDTO votoRequestDTO = new VotoResponseDTO();
        votoRequestDTO.setOpcaoVoto(OpcaoVoto.SIM);
        votoRequestDTO.setDescricao("Descrição do Voto");

        String invalidRequestJson = objectMapper.writeValueAsString(votoRequestDTO);

        mockMvc.perform(post("/pauta/iniciarVotacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void contarVotos() throws Exception {

        Map<OpcaoVoto, Long> contagemVotos = new HashMap<>();
        contagemVotos.put(OpcaoVoto.SIM, 5L);
        contagemVotos.put(OpcaoVoto.NAO, 3L);

        when(pautaService.contarVotos(1L)).thenReturn(contagemVotos);

        mockMvc.perform(get("/pauta/{pautaId}/contagem-votos", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.SIM").value(5L))
                .andExpect(jsonPath("$.NAO").value(3L));
    }


}





