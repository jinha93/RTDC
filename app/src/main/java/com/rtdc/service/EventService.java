package com.rtdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.Event;
import com.rtdc.repository.EventRepository;


@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	public Page<Event> getEventList(Pageable pageable, String status){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 12, Sort.by("eventId").descending());
		
		return eventRepository.findByStatus(pageable, status);
	}
	
}
