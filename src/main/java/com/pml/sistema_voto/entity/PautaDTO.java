package com.pml.sistema_voto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PautaDTO {

    private Long id;
    private String descricao;
    private LocalDateTime tempoPauta;

}
