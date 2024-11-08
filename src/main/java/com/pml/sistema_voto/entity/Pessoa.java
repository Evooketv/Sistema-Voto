package com.pml.sistema_voto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CPF;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Voto voto;

    public static String gerarCPF() {
        Random random = new Random();
        long numeroCpf = (long) (random.nextDouble() * 1_000_000_000_00L);
        return String.format("%011d", numeroCpf);
    }

}
