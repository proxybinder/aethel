package com.aethel.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aethel.core.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    // Spring Data JPA automatically provides save(), findById(), findAll(), delete()
}