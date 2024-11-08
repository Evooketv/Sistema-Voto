package com.pml.sistema_voto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoResponseDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "O campo pautaId é obrigatório.")
    private Long pautaId;

    private OpcaoVoto opcaoVoto;
    @JsonIgnore
    private Boolean opcaoSim;
    private String descricao;

    @NotNull(message = "O campo cpf é obrigatório.")
    private String cpf;

    private String mensagemErro;
    private String mensagemSucesso;

    public VotoResponseDTO(Voto voto) {
        this.id = voto.getId();
        this.pautaId = voto.getPauta() != null ? voto.getPauta().getId() : null;
        this.opcaoVoto = voto.getOpcao();
        this.descricao = voto.getDescricao();
        this.cpf = voto.getPessoa() != null ? voto.getPessoa().getCPF() : null;
    }
}