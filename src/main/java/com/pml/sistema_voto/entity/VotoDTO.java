package com.pml.sistema_voto.entity;


import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {

    private Long id;
    private OpcaoVoto opcao;
    private Long pautaId;
}
