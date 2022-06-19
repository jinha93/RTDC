package com.rtdc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.ToyRoom;
import com.rtdc.repository.ToyRoomRepository;


@Service
public class ToyRoomService {
	
	@Autowired
	private ToyRoomRepository toyRoomRepository;
	
	public List<ToyRoom> getToyRoomList(){
		return toyRoomRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
	}

	public void save(@Valid ToyRoom toyRoom) {
		toyRoomRepository.save(toyRoom);
	}
	
}
