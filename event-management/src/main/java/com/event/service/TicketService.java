package com.event.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.event.model.Event;
import com.event.model.Ticket;
import com.event.model.User;
import com.event.repository.EventRepository;
import com.event.repository.TicketRepository;
import com.event.repository.UserRepository;

@Service
public class TicketService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    
    public TicketService(EventRepository eventRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }
    
    public String bookTicket(Long eventId, String username, int quantity) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            return "Event not found";
        }
        Event event = eventOpt.get();
        if (event.getAvailableSeats() < quantity) {
            return "No available seats";
        }
        
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "User not found";
        }
        
        event.setAvailableSeats(event.getAvailableSeats() - quantity);
        eventRepository.save(event);
        
        Ticket ticket = new Ticket(event, user, quantity);
        ticketRepository.save(ticket);
        
        return "Ticket booked successfully";
    }
    
    public List<Ticket> getTicketsByUsername(String username) {
        return ticketRepository.findByUserUsername(username);
    }
    
    public boolean deleteTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            Event event = ticket.getEvent();
            event.setAvailableSeats(event.getAvailableSeats() + ticket.getQuantity());
            eventRepository.save(event);
            
            ticketRepository.delete(ticket);
            return true;
        }
        return false;
    }
}