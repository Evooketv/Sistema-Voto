package com.pml.sistema_voto.service;

import com.pml.sistema_voto.entity.*;
import com.pml.sistema_voto.entity.enums.OpcaoVoto;
import com.pml.sistema_voto.entity.exceptions.PautaNaoEncontradaException;
import com.pml.sistema_voto.repositories.PautaRepository;
import com.pml.sistema_voto.repositories.PessoaRepository;
import com.pml.sistema_voto.repositories.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PautaService {

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final PessoaRepository pessoaRepository;

    public PautaResponseDTO createPauta(PautaDTO pautaDTO) {
        // Verificação de nulo
        if (pautaDTO == null) {
            throw new IllegalArgumentException("PautaDTO não pode ser nulo.");
        }

        LocalDateTime agora = LocalDateTime.now();

        // Validações de data
        if (pautaDTO.getInicioPauta() == null) {
            pautaDTO.setInicioPauta(agora);
        } else if (pautaDTO.getInicioPauta().isBefore(agora)) {
            throw new IllegalArgumentException("A data de início da pauta não pode ser no passado.");
        }

        if (pautaDTO.getFimPauta() == null) {
            pautaDTO.setFimPauta(pautaDTO.getInicioPauta().plusMinutes(1));
        } else if (pautaDTO.getFimPauta().isBefore(pautaDTO.getInicioPauta())) {
            throw new IllegalArgumentException("A data de término da pauta deve ser após a data de início.");
        }

        // Criação da pauta
        Pauta pauta = new Pauta();
        pauta.setDescricao(pautaDTO.getDescricao());
        pauta.setInicioPauta(pautaDTO.getInicioPauta());
        pauta.setFimPauta(pautaDTO.getFimPauta());


        pauta = pautaRepository.save(pauta);


        return new PautaResponseDTO(pauta.getId(), pauta.getDescricao(), pauta.getInicioPauta(), pauta.getFimPauta(), new ArrayList<>());
    }


    public  VotoResponseDTO iniciarVotacao(Long pautaId, OpcaoVoto opcaoVoto, String cpf) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new PautaNaoEncontradaException("Pauta não encontrada"));

        if (cpf == null || cpf.trim().isEmpty()) {

            return new VotoResponseDTO(null, pautaId, opcaoVoto, null, "Descrição do Voto", null, null, "Voto registrado com sucesso (anônimo)");
        }

        Long cpfLong;
        try {
            cpfLong = Long.valueOf(cpf);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("CPF deve ser um número válido.");
        }

        Pessoa pessoa = pessoaRepository.findByCpf(cpfLong);
        if (pessoa == null) {
            pessoa = new Pessoa();
            pessoa.setCPF(String.valueOf(cpfLong));
            pessoa = pessoaRepository.save(pessoa);
        }


        if (votoRepository.existsByPautaAndPessoa(pauta, pessoa)) {
            throw new IllegalArgumentException("Essa pessoa já votou nesta pauta.");
        }

        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(pauta.getInicioPauta()) || agora.isAfter(pauta.getFimPauta())) {
            throw new IllegalArgumentException("A votação não está aberta para esta pauta.");
        }

        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setPessoa(pessoa);
        voto.setOpcao(opcaoVoto);

        voto = votoRepository.save(voto);

        return new VotoResponseDTO(
                voto.getId(),
                pauta.getId(),
                voto.getOpcao(),
                voto.getOpcao() == OpcaoVoto.SIM,
                pauta.getDescricao(),
                cpf,
                null,
                "Voto registrado com sucesso"
        );
    }


    public Map<OpcaoVoto, Long> contarVotos(Long pautaId) {
        Long votosSim = votoRepository.countByPautaIdAndOpcao(pautaId, OpcaoVoto.SIM);
        Long votosNao = votoRepository.countByPautaIdAndOpcao(pautaId, OpcaoVoto.NAO);

        Map<OpcaoVoto, Long> resultado = new HashMap<>();
        resultado.put(OpcaoVoto.SIM, votosSim);
        resultado.put(OpcaoVoto.NAO, votosNao);

        return resultado;
    }

    public String obterResultadoPauta(Long pautaId) {
        Map<OpcaoVoto, Long> votos = contarVotos(pautaId);
        long votosSim = votos.getOrDefault(OpcaoVoto.SIM, 0L);
        long votosNao = votos.getOrDefault(OpcaoVoto.NAO, 0L);

        String resultado;
        if (votosSim > votosNao) {
            resultado = "Aprovada";
        } else if (votosNao > votosSim) {
            resultado = "Reprovada";
        } else {
            resultado = "Empate";
        }

        return String.format(
                "Resultado da pauta: SIM: %d votos, NÃO: %d votos. O resultado da pauta foi %s.",
                votosSim, votosNao, resultado
        );
    }

    public Optional<Pauta> buscarPautaPorId(Long id) {
        return pautaRepository.findById(id);
    }

    public List<Pauta> getAllPautas() {
        return pautaRepository.findAll();
    }
}





