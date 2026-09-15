package com.aethel.core.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aethel.core.entity.Ticket;
import com.aethel.core.entity.TicketPriority;
import com.aethel.core.entity.TicketStatus;
import com.aethel.core.repository.TicketRepository;

// Data Transfer Object for incoming ticket requests
record CreateTicketRequest(String subject, String description, TicketPriority priority) {}

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*") // Allows Next.js frontend to call this endpoint
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSubject(request.subject());
        ticket.setDescription(request.description());
        ticket.setPriority(request.priority() != null ? request.priority() : TicketPriority.MEDIUM);
        ticket.setStatus(TicketStatus.NEW);

        // SLA Engine: Compute target resolution deadline dynamically
        Instant now = Instant.now();
        Instant deadline = switch (ticket.getPriority()) {
            case CRITICAL -> now.plus(Duration.ofHours(1));
            case HIGH -> now.plus(Duration.ofHours(4));
            case MEDIUM -> now.plus(Duration.ofHours(24));
            case LOW -> now.plus(Duration.ofHours(48));
        };
        ticket.setSlaDeadline(deadline);

        Ticket savedTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }
}