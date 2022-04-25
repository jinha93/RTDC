package com.rtdc.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.Board;
import com.rtdc.model.Event;
import com.rtdc.model.Post;
import com.rtdc.repository.EventRepository;
import com.rtdc.repository.PostRepository;


@Service
public class EventService {
	
//	private final EntityManager em;
	
	private EventRepository eventRepository;
	
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	public Page<Event> getPostList(Pageable pageable){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 12, Sort.by("event_id").descending());
		
		Post post = new Post();
		post.setTitle(searchText);
		post.setContent(searchText);
		post.setBoard(board);
		
		return eventRepository.findByBoardAndTitleContainingOrContentContaining(post, pageable);
	}
	
}
