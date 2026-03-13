package auca.ac.rw.CustomerComplaintSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.CustomerComplaintSystem.domain.Ticket;
import auca.ac.rw.CustomerComplaintSystem.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // POST /api/tickets/save?userId=xxx
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTicket(
            @RequestBody Ticket ticket,
            @RequestParam String userId) {

        String response = ticketService.saveTicket(ticket, userId);

        if (response.equals("Ticket saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // POST /api/tickets/assign
   @PostMapping(value = "/assign", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignStaff(@RequestBody AssignRequest request) {

        String response = ticketService.assignStaffToTicket(request.ticketId, request.staffId);

        if (response.equals("Staff assigned to ticket successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/tickets/all
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    //  NEW — Update ticket status
    // Employee sends ticketId and the new status in the body
    @PutMapping(value = "/updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTicketStatus(@RequestBody UpdateStatusRequest request) {

        String response = ticketService.updateTicketStatus(request.ticketId, request.status);

        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //  NEW — Delete a resolved ticket
    // Employee sends the ticketId in the body
    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteResolvedTicket(@RequestBody DeleteRequest request) {

        String response = ticketService.deleteResolvedTicket(request.ticketId);

        if (response.equals("Resolved ticket deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //  EXISTING inner class
    public static class AssignRequest {
        public String ticketId;
        public String staffId;
    }

    //  NEW inner class — for update status request
    public static class UpdateStatusRequest {
        public String ticketId;
        public String status;
    }

    //  NEW inner class — for delete request
    public static class DeleteRequest {
        public String ticketId;
    }
}


