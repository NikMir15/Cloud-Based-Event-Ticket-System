package com.event.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.model.Event;
import com.event.repository.EventRepository;
import com.event.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {

	@Autowired
    EventRepository eventRepository;
    
	@Autowired
    TicketRepository ticketRepository;
    
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    public Optional<Event> updateEvent(Long id, Event eventDetails) {
        return eventRepository.findById(id).map(event -> {
            event.setTitle(eventDetails.getTitle());
            event.setPlace(eventDetails.getPlace());
            event.setDate(eventDetails.getDate());
            event.setPrice(eventDetails.getPrice());
            event.setAvailableSeats(eventDetails.getAvailableSeats());
            return eventRepository.save(event);
        });
    }
    
    @Transactional
    public boolean deleteEvent(Long id) {
    	return eventRepository.findById(id).map(event -> {
            ticketRepository.deleteByEvent(event);
            eventRepository.delete(event);
            return true;
        }).orElse(false);
    }

}