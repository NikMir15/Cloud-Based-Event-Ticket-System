package com.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.model.Ticket;
import com.event.service.TicketService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class TicketController {

    private final TicketService ticketService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    @PostMapping("/book/{eventId}")
    public ResponseEntity<?> bookTicket(@PathVariable Long eventId,
                                        @RequestParam String username,
                                        @RequestParam(defaultValue = "1") int quantity) {
        String result = ticketService.bookTicket(eventId, username, quantity);
        if ("Ticket booked successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
    
    @GetMapping("/myTickets")
    public ResponseEntity<List<Ticket>> getMyTickets(@RequestParam String username) {
        List<Ticket> tickets = ticketService.getTicketsByUsername(username);
        return ResponseEntity.ok(tickets);
    }
    
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long ticketId) {
        boolean deleted = ticketService.deleteTicket(ticketId);
        if (deleted) {
            return ResponseEntity.ok("Ticket deleted successfully");
        }
        return ResponseEntity.badRequest().body("Failed to delete ticket");
    }
}