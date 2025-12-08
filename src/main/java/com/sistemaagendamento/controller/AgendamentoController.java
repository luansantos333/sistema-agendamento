package com.sistemaagendamento.controller;

import com.sistemaagendamento.dto.AgendamentoCreateRequest;
import com.sistemaagendamento.dto.AgendamentoResponse;
import com.sistemaagendamento.dto.AgendamentoUpdateRequest;
import com.sistemaagendamento.service.AgendamentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping ("/api/v1/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping ("/{id}")
    public ResponseEntity<AgendamentoResponse> findById (@PathVariable Long id) {


        AgendamentoResponse appointmentInfoById = service.getAppointmentInfoById(id);
        return ResponseEntity.ok(appointmentInfoById);

    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> createNewAppointment (@RequestBody AgendamentoCreateRequest request) {

        AgendamentoResponse newAppointment = service.createNewAppointment(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newAppointment.id()).toUri();

        return ResponseEntity.created(uri).body(newAppointment);

    }

    @PutMapping ("/appointment/cancel/{id}")
    public ResponseEntity<AgendamentoResponse> cancelAppointment (@PathVariable Long id) {

        AgendamentoResponse agendamentoResponse = service.cancelAppointment(id);

        return ResponseEntity.ok(agendamentoResponse);

    }

    @PutMapping ("/appointment/conclude/{id}")
    public ResponseEntity<AgendamentoResponse> concludeAppointment (@PathVariable Long id) {


        AgendamentoResponse agendamentoResponse = service.concludeAppointment(id);
        return ResponseEntity.ok(agendamentoResponse);

    }

    @PutMapping ("/{id}")
    public ResponseEntity <AgendamentoResponse> updateAppointment (@RequestBody AgendamentoUpdateRequest update, @PathVariable Long id) {

        AgendamentoResponse agendamentoResponse = service.updateAppointment(id, update);

        return ResponseEntity.ok(agendamentoResponse);


    }

}
