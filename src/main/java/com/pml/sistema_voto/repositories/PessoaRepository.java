package com.pml.sistema_voto.repositories;

import com.pml.sistema_voto.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.CPF = :cpf")
    Pessoa findByCpf(@Param("cpf") Long cpf);
}
