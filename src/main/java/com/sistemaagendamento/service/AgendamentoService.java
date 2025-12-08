package com.sistemaagendamento.service;

import com.sistemaagendamento.dto.AgendamentoCreateRequest;
import com.sistemaagendamento.dto.AgendamentoResponse;
import com.sistemaagendamento.dto.AgendamentoUpdateRequest;
import com.sistemaagendamento.entities.Agendamento;
import com.sistemaagendamento.entities.Status;
import com.sistemaagendamento.mapper.AgendamentoMapper;
import com.sistemaagendamento.repository.AgendamentoRepository;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }


    @org.springframework.transaction.annotation.Transactional
    public AgendamentoResponse createNewAppointment(@Valid AgendamentoCreateRequest request) {

        validateDateTimeInterval(request.dataInicio(), request.dataFim());
        validateScheduleConflict(request.usuario(), request.dataInicio(), request.dataFim(), null);
        Agendamento newAppointment = repository.save(AgendamentoMapper.toEntity(request));
        return AgendamentoMapper.toResponseDTO(newAppointment);


    }

    @org.springframework.transaction.annotation.Transactional
    public AgendamentoResponse updateAppointment (Long id, @Valid AgendamentoUpdateRequest update) {

        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new NoSuchElementException("No appointment found with this id"));
        AgendamentoMapper.merge(agendamento, update);
        validateDateTimeInterval(update.dataInicio(), update.dataFim());
        validateScheduleConflict(agendamento.getUsuario(), update.dataInicio(), update.dataFim(), agendamento.getId());

        return AgendamentoMapper.toResponseDTO(agendamento);
    }

    @org.springframework.transaction.annotation.Transactional
    public AgendamentoResponse cancelAppointment (Long id) {

        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new NoSuchElementException("No appointment found!"));
        agendamento.setStatus(Status.CANCELADO);
        Agendamento canceledAppointment = repository.save(agendamento);
        return AgendamentoMapper.toResponseDTO(canceledAppointment);

    }

    @org.springframework.transaction.annotation.Transactional (readOnly = true)
    public AgendamentoResponse getAppointmentInfoById (Long id) {

        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new NoSuchElementException("No appointment found"));

        return AgendamentoMapper.toResponseDTO(agendamento);

    }

    @Transactional
    public AgendamentoResponse concludeAppointment (Long id) {

        Agendamento agendamento = repository.findById(id).orElseThrow(() -> new NoSuchElementException("No appointment found!"));

        if (!agendamento.getDataFim().isBefore(LocalDateTime.now())) {

            throw new IllegalArgumentException("Cannot conclude a appointment before it's end time");

        }

        agendamento.setStatus(Status.CONCLUIDO);

        Agendamento concludedAppointment = repository.save(agendamento);

        return AgendamentoMapper.toResponseDTO(concludedAppointment);


    }


    private void validateDateTimeInterval(LocalDateTime inicio, LocalDateTime fim) {


        if (inicio == null | fim == null | fim.isBefore(inicio)) {


            throw new IllegalArgumentException("Invalid period set up: end date cannot be before start date!");

        }



    }

    private void validateScheduleConflict(String usuario, LocalDateTime dataInicio, LocalDateTime dataFim, Long ignoreId) {

        if (repository.existsConflito(usuario, dataInicio, dataFim, ignoreId)) {


            throw new DuplicateRequestException("There is already an appointment scheduled for the period");

        }


    }

}
