package com.sistemaagendamento.mapper;

import com.sistemaagendamento.dto.AgendamentoCreateRequest;
import com.sistemaagendamento.dto.AgendamentoResponse;
import com.sistemaagendamento.dto.AgendamentoUpdateRequest;
import com.sistemaagendamento.entities.Agendamento;
import com.sistemaagendamento.entities.Status;

import java.time.Instant;

public class AgendamentoMapper {

    public static Agendamento toEntity(AgendamentoCreateRequest request) {

        return Agendamento.builder()
                .titulo(request.titulo())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .atualizadoEm(Instant.now())
                .descricao(request.descricao())
                .usuario(request.usuario())
                .status(Status.AGENDADO)
                .criadoEm(Instant.now())
                .build();
    }

    public static AgendamentoResponse toResponseDTO (Agendamento agendamento) {


        return new AgendamentoResponse(agendamento.getId(), agendamento.getTitulo(),agendamento.getDescricao(),
                agendamento.getDataInicio(), agendamento.getStatus(), agendamento.getDataFim(),
                agendamento.getCriadoEm(), agendamento.getAtualizadoEm());


    }

    public static void merge (Agendamento entity, AgendamentoUpdateRequest req) {

        if (req.titulo() != null) {

            entity.setTitulo(req.titulo());

        }

        if (req.descricao() != null) {

            entity.setDescricao(req.descricao());

        }

        if (req.dataInicio() != null) {


            entity.setDataInicio(req.dataInicio());

        }

        if (req.dataFim() != null) {


            entity.setDataFim(req.dataFim());

        }



    }

}
