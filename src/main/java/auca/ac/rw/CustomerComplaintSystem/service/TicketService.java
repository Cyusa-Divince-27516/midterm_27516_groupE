package auca.ac.rw.CustomerComplaintSystem.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.CustomerComplaintSystem.domain.ETicketStatus;
import auca.ac.rw.CustomerComplaintSystem.domain.SupportStaff;
import auca.ac.rw.CustomerComplaintSystem.domain.Ticket;
import auca.ac.rw.CustomerComplaintSystem.domain.User;
import auca.ac.rw.CustomerComplaintSystem.repository.SupportStaffRepository;
import auca.ac.rw.CustomerComplaintSystem.repository.TicketRepository;
import auca.ac.rw.CustomerComplaintSystem.repository.UserRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SupportStaffRepository staffRepo;

    
     // Saves a new ticket and links it to the user who created it.
     
    public String saveTicket(Ticket ticket, String userId) {
        if (userId == null) {
            return "User ID is required.";
        }

        User user = userRepo.findById(UUID.fromString(userId)).orElse(null);
        if (user == null) {
            return "User not found.";
        }

        // Link ticket to user (One-to-Many: User → Ticket)
        ticket.setCreatedBy(user);
        ticketRepo.save(ticket);
        return "Ticket saved successfully.";
    }

    // 
    //  Assigns a support staff member to a ticket.
    //  This uses the Many-to-Many relationship — adds to the join table.
     

   
    public String assignStaffToTicket(String ticketId, String staffId) {
        Ticket ticket = ticketRepo.findById(UUID.fromString(ticketId)).orElse(null);
        if (ticket == null) {
            return "Ticket not found.";
        }

        SupportStaff staff = staffRepo.findById(UUID.fromString(staffId)).orElse(null);
        if (staff == null) {
            return "Staff not found.";
        }

        // Add staff to the ticket's list — this inserts a row in ticket_staff table
        ticket.getAssignedStaff().add(staff);
        ticketRepo.save(ticket);
        return "Staff assigned to ticket successfully.";
    }

    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    // Update ticket status
    // Employee picks a ticket by ID and changes its status
    public String updateTicketStatus(String ticketId, String newStatus) {

        // Find the ticket first
        Ticket ticket = ticketRepo.findById(UUID.fromString(ticketId)).orElse(null);

        if (ticket == null) {
            return "Ticket not found.";
        }

        // Try to convert the string to ETicketStatus enum
        // If someone sends "INVALID", this will throw an error — we catch it below
        try {
            ETicketStatus status = ETicketStatus.valueOf(newStatus.toUpperCase());
            ticket.setStatus(status);
            ticketRepo.save(ticket);
            return "Ticket status updated to " + status + " successfully.";
        } catch (IllegalArgumentException e) {
            return "Invalid status. Allowed values: OPEN, IN_PROGRESS, RESOLVED, CLOSED.";
        }
    }

    //Delete a resolved ticket
    // Only allows deletion if the ticket status is RESOLVED
    public String deleteResolvedTicket(String ticketId) {

        Ticket ticket = ticketRepo.findById(UUID.fromString(ticketId)).orElse(null);

        if (ticket == null) {
            return "Ticket not found.";
        }

        // Safety check — only RESOLVED tickets can be deleted
        if (!ticket.getStatus().equals(ETicketStatus.RESOLVED)) {
            return "Only RESOLVED tickets can be deleted. Current status: " + ticket.getStatus();
        }

        ticketRepo.delete(ticket);
        return "Resolved ticket deleted successfully.";
    }
}

