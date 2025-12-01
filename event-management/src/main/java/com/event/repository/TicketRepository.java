package com.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event.model.Event;
import com.event.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findByUserUsername(String username);
	void deleteByEvent(Event event);

}
