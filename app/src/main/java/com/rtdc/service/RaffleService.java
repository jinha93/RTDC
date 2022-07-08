package com.rtdc.service;

import java.util.List;

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
	
	public List<Raffle> getRaffleList(){
//		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
//		pageable = PageRequest.of(page, 6, Sort.by("raffleId").descending());
		return raffleRepository.findAll(Sort.by(Sort.Direction.DESC, "raffleId"));
	}
	
	public Raffle getRaffle(long raffleId) {
		return raffleRepository.findByRaffleId(raffleId);
	}
	
	public Raffle save(Raffle event) {
		return raffleRepository.save(event);
	}
	
}
