package com.pml.sistema_voto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VotoRequestDTO {
    private Long pautaId;
    private String descricao;
    private String opcaoVoto;


}
