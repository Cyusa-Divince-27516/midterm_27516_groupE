package auca.ac.rw.CustomerComplaintSystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.CustomerComplaintSystem.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    // Check if a ticket with the same title already exists
    boolean existsByTitle(String title);
}