package com.pml.sistema_voto.repositories;

import com.pml.sistema_voto.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long > {
}
