package com.rtdc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

	Page<Event> findByStatus(Pageable pageable, String status);

}
