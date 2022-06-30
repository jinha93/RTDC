package com.rtdc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long>{

	Page<Raffle> findByStatus(Pageable pageable, String status);
	
	Raffle findByRaffleId(Long raffleId);

}
