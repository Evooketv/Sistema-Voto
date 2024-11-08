package com.pml.sistema_voto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class PautaResponseDTO {

    private Long id;
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime inicioPauta;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fimPauta;

    private List<VotoDTO> votos = new ArrayList<>();


    public PautaResponseDTO(Long id, String descricao, LocalDateTime inicioPauta, LocalDateTime fimPauta, List<VotoDTO> votos) {
        this.id = id;
        this.descricao = descricao;
        this.inicioPauta = inicioPauta;
        this.fimPauta = fimPauta;
        this.votos = votos;
    }
}
