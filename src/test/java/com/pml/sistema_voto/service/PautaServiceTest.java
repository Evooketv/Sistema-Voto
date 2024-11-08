package com.pml.sistema_voto.service;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.PautaDTO;
import com.pml.sistema_voto.entity.PautaResponseDTO;
import com.pml.sistema_voto.entity.Voto;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.entity.exceptions.InvalidDocumentException;
import com.pml.sistema_voto.entity.exceptions.PautaNaoEncontradaException;
import com.pml.sistema_voto.repositories.PautaRepository;
import com.pml.sistema_voto.repositories.PessoaRepository;
import com.pml.sistema_voto.repositories.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Nested
class PautaServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @InjectMocks
    private VotoService votoService;

    @Mock
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPauta() throws InvalidDocumentException {

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setDescricao("Descricao Pauta");
        pautaDTO.setInicioPauta(LocalDateTime.now().plusMinutes(1));
        pautaDTO.setFimPauta(LocalDateTime.now().plusMinutes(2));

        Pauta pautaSalva = new Pauta();
        pautaSalva.setId(1L);
        pautaSalva.setDescricao(pautaDTO.getDescricao());
        pautaSalva.setInicioPauta(pautaDTO.getInicioPauta());
        pautaSalva.setFimPauta(pautaDTO.getFimPauta());

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        PautaResponseDTO response = pautaService.createPauta(pautaDTO);

        assertNotNull(response);
        assertEquals(pautaSalva.getId(), response.getId());
        assertEquals(pautaDTO.getDescricao(), response.getDescricao());
        assertEquals(pautaDTO.getInicioPauta(), response.getInicioPauta());
        assertEquals(pautaDTO.getFimPauta(), response.getFimPauta());
    }


    @Test
    void iniciarVotacaoVotoEPautaValidos() {

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Voto voto = new Voto();
        voto.setPauta(pauta);

        when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));
        when(votoRepository.save(voto)).thenReturn(voto);

        Voto resultado = votoService.registrarVoto(voto);

        assertEquals(voto, resultado);

        verify(pautaRepository, times(1)).findById(pauta.getId());
        verify(votoRepository, times(1)).save(voto);

    }

    @Test
    void iniciarVotacaoPautaNaoEncontrada() {

      Long pautaId = 2L;

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> pautaService.iniciarVotacao(pautaId, OpcaoVoto.SIM, "12345678900"));

        verify(pautaRepository, times(1)).findById(pautaId);

        verify(votoRepository, never()).save(any());
    }


    @Test
    void contarVotos() {

        Long pautaID = 1L;
        when(votoRepository.countByPautaIdAndOpcao(pautaID, OpcaoVoto.SIM)).thenReturn(5L);
        when(votoRepository.countByPautaIdAndOpcao(pautaID, OpcaoVoto.NAO)).thenReturn(3L);

        Map<OpcaoVoto, Long> resultado = pautaService.contarVotos(pautaID);

        assertEquals(5L, resultado.get(OpcaoVoto.SIM));
        assertEquals(3L, resultado.get(OpcaoVoto.NAO));

        verify(votoRepository, times(1)).countByPautaIdAndOpcao(pautaID, OpcaoVoto.SIM);
        verify(votoRepository, times(1)).countByPautaIdAndOpcao(pautaID, OpcaoVoto.NAO);
    }


    @Test
    void obterResultadoPauta() {
    }

    @Test
    void getAllPautas() {
    }

}
