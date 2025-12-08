package com.sistemaagendamento.dto;

import com.sistemaagendamento.entities.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AgendamentoResponse(Long id, String titulo, String descricao,
                                  LocalDateTime dataInicio, Status statusAgendamento, LocalDateTime dataFim, Instant criadoEm, Instant atualizadoEm) {


}
