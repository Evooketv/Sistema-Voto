package com.pml.sistema_voto.controller;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.PautaDTO;
import com.pml.sistema_voto.entity.PautaResponseDTO;
import com.pml.sistema_voto.entity.VotoResponseDTO;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/pauta")
@CrossOrigin(origins = "*")

public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping("/create")
    public ResponseEntity<?> criarPauta(@RequestBody @Valid PautaDTO pautaDTO) {
        try {
            PautaResponseDTO pautaResponseDTO = pautaService.createPauta(pautaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pautaResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PostMapping("/iniciarVotacao")
    public ResponseEntity<VotoResponseDTO> iniciarVotacao(@RequestBody @Valid VotoResponseDTO votoRequestDTO) {

        List<String> erros = new ArrayList<>();

        if (votoRequestDTO.getPautaId() == null) {
            erros.add("O campo pautaId é obrigatório.");
        }
        if (votoRequestDTO.getCpf() == null) {
            erros.add("O campo cpf é obrigatório.");
        }

        if (!erros.isEmpty()) {
            return ResponseEntity.badRequest().body(new VotoResponseDTO(null, null, null, false, String.join(", ", erros), votoRequestDTO.getCpf(), null, null));
        }

        try {
            VotoResponseDTO response = pautaService.iniciarVotacao(votoRequestDTO.getPautaId(), votoRequestDTO.getOpcaoVoto(), votoRequestDTO.getCpf());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new VotoResponseDTO(null, null, null, false, e.getMessage(), votoRequestDTO.getCpf(), null, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new VotoResponseDTO(null, null, null, false, e.getMessage(), votoRequestDTO.getCpf(), null, null));
        }
    }

    @GetMapping("/{pautaId}/contagem-votos")
    public Map<OpcaoVoto, Long> getContagemVotos(@PathVariable Long pautaId) {
        return pautaService.contarVotos(pautaId);
    }

    @GetMapping("/{pautaId}/resultado")
    public ResponseEntity<String> obterResultadoPauta(@PathVariable Long pautaId) {
        String resultado = pautaService.obterResultadoPauta(pautaId).toString();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pauta>> getAllPautas() {
        List<Pauta> pautas = pautaService.getAllPautas();
        return ResponseEntity.ok(pautas);
    }

}



