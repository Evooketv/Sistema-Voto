package com.pml.sistema_voto.repositories;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.Pessoa;
import com.pml.sistema_voto.entity.Voto;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    List<Voto> findByPautaId(Long pautaId);

    Long countByPautaIdAndOpcao(Long pautaId, OpcaoVoto opcao);

    boolean existsByPautaAndPessoa(Pauta pauta, Pessoa pessoa);
}
