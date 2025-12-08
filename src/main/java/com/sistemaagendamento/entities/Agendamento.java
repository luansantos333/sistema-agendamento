package com.sistemaagendamento.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "tb_agendamento")

public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column (columnDefinition = "TEXT")
    private String descricao;
    @Column (name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;
    @Column (name = "data_fim", nullable = false)
    private LocalDateTime dataFim;
    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 20)
    private Status status;
    @Column (nullable = false, length = 120)
    private String usuario;
    @Column (name = "criado_em", nullable = false)
    private Instant criadoEm;
    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;


}
