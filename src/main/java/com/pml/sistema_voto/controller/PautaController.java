package com.pml.sistema_voto.controller;

import com.pml.sistema_voto.entity.Pauta;
import com.pml.sistema_voto.entity.PautaDTO;
import com.pml.sistema_voto.entity.exceptions.InvalidDocumentException;
import com.pml.sistema_voto.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@ResponseBody
@RestController
@RequestMapping("/pauta")
@CrossOrigin(origins = "*")

public class PautaController {

    @Autowired
    private PautaService service;

    public ResponseEntity<?> createAccount(@Valid @RequestBody PautaDTO pautaDTO) {

        try {
            Pauta newAccount = this.service.createAccount(pautaDTO, pautaDTO.getDescricao());
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        } catch (InvalidDocumentException e) {
            return ResponseEntity.badRequest().body("Número de documento inválido: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a conta: " + e.getMessage());
        }

    }

}
