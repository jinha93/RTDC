package com.rtdc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtdc.model.Raffle;
import com.rtdc.model.RaffleTarget;
import com.rtdc.repository.RaffleTargetRepository;


@Service
public class RaffleTargetService {
	
	@Autowired
	private RaffleTargetRepository raffleTargetRepository;
	
	public RaffleTarget save(RaffleTarget raffleTarget) {
		return raffleTargetRepository.save(raffleTarget);
	}
	
	public List<RaffleTarget> getRaffleTargetList(Raffle raffle){
		return raffleTargetRepository.findByRaffle(raffle);
	}
	
}
