package com.pml.sistema_voto.service;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.Pessoa;
import com.pml.sistema_voto.entity.Voto;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.repositories.PautaRepository;
import com.pml.sistema_voto.repositories.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Nested
class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @InjectMocks
    private VotoService votoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarVoto() {


        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setPessoa(pessoa);
        voto.setOpcao(OpcaoVoto.SIM);

        when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

        votoService.registrarVoto(voto);

        verify(pautaRepository, times(1)).findById(pauta.getId());

        verify(votoRepository, times(1)).save(voto);

    }

    @Test
    void registrarVoto_PautaNaoEncontrada() {

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setPessoa(pessoa);
        voto.setOpcao(OpcaoVoto.SIM);

        when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> votoService.registrarVoto(voto), "Pauta não encontrada");

        verify(pautaRepository, times(1)).findById(pauta.getId());

        verify(votoRepository, never()).save(voto);
    }

}