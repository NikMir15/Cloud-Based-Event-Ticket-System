package com.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}