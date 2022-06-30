package com.rtdc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.Raffle;
import com.rtdc.repository.RaffleRepository;


@Service
public class RaffleService {
	
	@Autowired
	private RaffleRepository raffleRepository;
	
	public Page<Raffle> getRaffleList(Pageable pageable, String status){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 6, Sort.by("raffleId").descending());
		
		return raffleRepository.findByStatus(pageable, status);
	}
	
	public Raffle getRaffle(long raffleId) {
		return raffleRepository.findByRaffleId(raffleId);
	}
	
	public Raffle save(Raffle event) {
		return raffleRepository.save(event);
	}
	
}
