package com.rtdc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtdc.model.Holder;
import com.rtdc.model.User;
import com.rtdc.repository.HolderRepository;


@Service
public class HolderService {
	
	@Autowired
	private HolderRepository holderRepository;
	
	public Holder save(Holder holder) {
		return holderRepository.save(holder);
	}
	
	public Holder getHolder(String gb, String tokenId) {
		return holderRepository.findByGbAndTokenIdAndDelYn(gb, tokenId, "N");
	}
	
	public List<Holder> getHolderList(String gb, User user){
		return holderRepository.findByGbAndUserAndDelYn(gb, user, "N");
	}
	
	
}
