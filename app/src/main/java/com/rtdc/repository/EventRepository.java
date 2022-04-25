package com.rtdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtdc.model.Board;
import com.rtdc.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
