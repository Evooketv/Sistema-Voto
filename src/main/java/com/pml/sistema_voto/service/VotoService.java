package com.pml.sistema_voto.service;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.Voto;
import com.pml.sistema_voto.repositories.PautaRepository;
import com.pml.sistema_voto.repositories.VotoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class VotoService {

    @Autowired
    PautaService pautaService;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public Voto registrarVoto(Voto voto) {

        Pauta pauta = pautaRepository.findById(voto.getPauta().getId())
                .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));

        voto.setPauta(pauta);
        return votoRepository.save(voto);
    }
}
