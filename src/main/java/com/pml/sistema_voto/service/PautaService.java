package com.pml.sistema_voto.service;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.PautaDTO;
import com.pml.sistema_voto.repositories.PautaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;


    public Pauta createAccount(PautaDTO pautaDTO, String descricao) {

        Pauta pauta = new Pauta();
        pauta.setDescricao(pautaDTO.getDescricao());

        return pautaRepository.save(pauta);

    }

}
