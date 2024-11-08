package com.pml.sistema_voto.controller;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.Voto;
import com.pml.sistema_voto.entity.VotoRequestDTO;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.repositories.PautaRepository;
import com.pml.sistema_voto.service.VotoService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;
    private final PautaRepository pautaRepository;

    @Autowired  // Construtor feito manualmente
    public VotoController(VotoService votoService, PautaRepository pautaRepository) {
        this.votoService = votoService;
        this.pautaRepository = pautaRepository;
    }

    @PostMapping
    public ResponseEntity<String> registrarVoto(@RequestBody VotoRequestDTO votoRequest) {
        try {
            Pauta pauta = pautaRepository.findById(votoRequest.getPautaId())
                    .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));

            Voto voto = new Voto();
            voto.setPauta(pauta);
            voto.setOpcao(OpcaoVoto.valueOf(votoRequest.getOpcaoVoto()));
            votoService.registrarVoto(voto);

            return ResponseEntity.ok("Voto registrado com sucesso");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


